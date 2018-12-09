package com.chaty.shrimpfarm.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chaty.shrimpfarm.model.Expense;
import com.chaty.shrimpfarm.model.Feed;
import com.chaty.shrimpfarm.model.Harvest;
import com.chaty.shrimpfarm.model.Pond;
import com.chaty.shrimpfarm.model.PondInfo;
import com.chaty.shrimpfarm.model.Sampling;
import com.chaty.shrimpfarm.model.Stock;
import com.chaty.shrimpfarm.model.Supplement;
import com.chaty.shrimpfarm.repository.ExpenseRepo;
import com.chaty.shrimpfarm.repository.FeedRepo;
import com.chaty.shrimpfarm.repository.HarvestRepo;
import com.chaty.shrimpfarm.repository.PondRepo;
import com.chaty.shrimpfarm.repository.SamplingRepo;
import com.chaty.shrimpfarm.repository.StockRepo;
import com.chaty.shrimpfarm.repository.SupplementRepo;

@RestController
@RequestMapping("/farm/api")
public class ShrimpFarmController {

	@Autowired
	FeedRepo feedEntryRepo;

	@Autowired
	ExpenseRepo expenseRepo;

	@Autowired
	HarvestRepo harvestRepo;

	@Autowired
	SamplingRepo samplingRepo;

	@Autowired
	SupplementRepo supplementRepo;

	@Autowired
	PondRepo pondRepo;

	@Autowired
	StockRepo stockRepo;

	@Autowired
	DataLoaderUtil util;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	// Dashboard Data

	@RequestMapping(path = "/getDashboard/{site}/{season}", method = RequestMethod.GET)
	public List<PondInfo> getDashboard(@PathVariable("site") String site, @PathVariable("season") String season) {

		List<PondInfo> pondInfoList = new ArrayList<>();

		List<Pond> pondList = getPondList();
		List<Feed> feedList = getFeedList("");
		List<Stock> stockList = getStockList();
		List<Sampling> samplingList = getSamplingList();
		List<Supplement> supplementList = getSupplementList();
		List<Harvest> harvestList = getHarvestList();

		pondInfoList = pondList.stream()
				.filter(pond -> pond.getSite().equalsIgnoreCase(site) && pond.getSeason().equalsIgnoreCase(season))
				.map(obj -> {

					PondInfo info = new PondInfo();

					info.setStock(stockList.stream().filter(
							s -> s.getPond().equals(obj.getNumber()) && s.getSite().equalsIgnoreCase(obj.getSite()))
							.findFirst().get());

					info.setFeed(feedList.stream().filter(
							f -> f.getSite().equalsIgnoreCase(obj.getSite()) && f.getPond().equals(obj.getNumber()))
							.collect(Collectors.toList()));

					info.setSupplement(supplementList.stream().filter(
							s -> s.getPond().equals(obj.getNumber()) && s.getSite().equalsIgnoreCase(obj.getSite()))
							.collect(Collectors.toList()));

					info.setHarvest(harvestList.stream().filter(
							s -> s.getPond().equals(obj.getNumber()) && s.getSite().equalsIgnoreCase(obj.getSite()))
							.collect(Collectors.toList()));

					info.setSampling(samplingList.stream().filter(
							s -> s.getPond().equals(obj.getNumber()) && s.getSite().equalsIgnoreCase(obj.getSite()))
							.collect(Collectors.toList()));

					Double total = info.getFeed().stream().mapToDouble(a -> a.getAmount()).sum();
					info.setFeedTotal(total.floatValue());
					
					System.out.println(total);
					
					Long days = ChronoUnit.DAYS.between(info.getStock().getDate(),LocalDate.now());
					info.setProgressDays(days.intValue());

					info.setNumber(obj.getNumber());
					info.setSite(obj.getSite());
					info.setSeason(obj.getSeason());
					info.setSize(1.0);

					return info;
				}).collect(Collectors.toList());

		return pondInfoList;

	}

	// Load Data

	@RequestMapping(path = "/loadFeed/{pond}/{stock}", method = RequestMethod.GET)
	public List<Feed> loadFeed(@PathVariable("pond") String pond, @PathVariable("stock") Integer stock) {
		return util.loadFeed(pond, stock);
	}

	@RequestMapping(path = "/loadHarvest/{pond}", method = RequestMethod.GET)
	public List<Harvest> loadHarvest(@PathVariable("pond") Integer pond) {
		return util.loadHarvest(pond);
	}

	@RequestMapping(path = "/loadSupplement/{pond}", method = RequestMethod.GET)
	public List<Supplement> loadSupplement(@PathVariable("pond") Integer pond) {
		return util.loadSupplement(pond);
	}

	@RequestMapping(path = "/loadSampling/{pond}", method = RequestMethod.GET)
	public List<Sampling> loadSampling(@PathVariable("pond") Integer pond) {
		return util.loadSampling(pond);
	}

	@RequestMapping(path = "/loadStock/{pond}", method = RequestMethod.GET)
	public List<Stock> loadStock(@PathVariable("pond") Integer pond) {
		return util.loadStock(pond);
	}

	@RequestMapping(path = "/loadPond/{site}/{numberofponds}", method = RequestMethod.GET)
	public List<Pond> loadPond(@PathVariable("site") String site,
			@PathVariable("numberofponds") Integer numberofponds) {
		return util.loadPond(site, numberofponds);
	}

	// Feed Entry Paths

	@RequestMapping(path = "/feedEntry", method = RequestMethod.POST)
	public Feed addFeedEntry(@Valid @RequestBody Feed entry) {
		return feedEntryRepo.save(entry);
	}

	@RequestMapping(path = "/addFeedList", method = RequestMethod.POST)
	public void addFeedEntryList(@Valid @RequestBody List<Feed> entry) {
		entry.stream().forEach(a -> feedEntryRepo.save(a));
	}

	@RequestMapping(path = "/getFeed/{id}", method = RequestMethod.GET)
	public Feed getFeedEntry(@PathVariable("id") String id) {
		return feedEntryRepo.findOne(id);
	}

	@RequestMapping(path = "/deleteFeed/{id}", method = RequestMethod.DELETE)
	public void deleteFeedEntry(@PathVariable("id") String id) {
		final Feed feed = feedEntryRepo.findOne(id);
		feedEntryRepo.delete(feed);
	}

	@RequestMapping(path = "/getFeedList", method = RequestMethod.GET)
	public List<Feed> getFeedList(@QueryParam("pond") String pond) {
		if (pond != null & pond != "") {
			return feedEntryRepo.findAll().stream().filter(a -> a.getPond().equals(pond)).collect(Collectors.toList());
		} else {
			return feedEntryRepo.findAll();
		}
	}

	// Expense Entry Paths

	@RequestMapping(path = "/expenseEntry", method = RequestMethod.POST)
	public Expense addExpenseEntry(@Valid @RequestBody Expense entry) {
		return expenseRepo.save(entry);
	}

	@RequestMapping(path = "/addExpenseList", method = RequestMethod.POST)
	public void addExpenseList(@Valid @RequestBody List<Expense> entry) {
		entry.stream().forEach(a -> expenseRepo.save(a));
	}

	@RequestMapping(path = "/deleteExpense/{id}", method = RequestMethod.DELETE)
	public void deleteExpense(@PathVariable("id") String id) {
		final Expense feed = expenseRepo.findOne(id);
		expenseRepo.delete(feed);
	}

	@RequestMapping(path = "/getExpenseList", method = RequestMethod.GET)
	public List<Expense> getExpenseList() {
		return expenseRepo.findAll();
	}

	// Sampling Entry Paths

	@RequestMapping(path = "/samplingEntry", method = RequestMethod.POST)
	public Sampling addSamplingEntry(@Valid @RequestBody Sampling entry) {
		return samplingRepo.save(entry);
	}

	@RequestMapping(path = "/addSamplingList", method = RequestMethod.POST)
	public void addSamplingList(@Valid @RequestBody List<Sampling> entry) {
		entry.stream().forEach(a -> samplingRepo.save(a));
	}

	@RequestMapping(path = "/deleteSampling/{id}", method = RequestMethod.DELETE)
	public void deleteSampling(@PathVariable("id") String id) {
		final Sampling feed = samplingRepo.findOne(id);
		samplingRepo.delete(feed);
	}

	@RequestMapping(path = "/getSamplingList", method = RequestMethod.GET)
	public List<Sampling> getSamplingList() {
		return samplingRepo.findAll();
	}

	// Supplement Entry Paths

	@RequestMapping(path = "/supplementEntry", method = RequestMethod.POST)
	public Supplement addSupplementEntry(@Valid @RequestBody Supplement entry) {
		return supplementRepo.save(entry);
	}

	@RequestMapping(path = "/addSupplementList", method = RequestMethod.POST)
	public void addSupplementList(@Valid @RequestBody List<Supplement> entry) {
		entry.stream().forEach(a -> supplementRepo.save(a));
	}

	@RequestMapping(path = "/deleteSupplement/{id}", method = RequestMethod.DELETE)
	public void deleteSupplement(@PathVariable("id") String id) {
		final Supplement feed = supplementRepo.findOne(id);
		supplementRepo.delete(feed);
	}

	@RequestMapping(path = "/getSupplementList", method = RequestMethod.GET)
	public List<Supplement> getSupplementList() {
		return supplementRepo.findAll();
	}

	// Harvest Entry Paths

	@RequestMapping(path = "/harvestEntry", method = RequestMethod.POST)
	public Harvest addharvestEntry(@Valid @RequestBody Harvest entry) {
		return harvestRepo.save(entry);
	}

	@RequestMapping(path = "/addHarvestList", method = RequestMethod.POST)
	public void addharvestList(@Valid @RequestBody List<Harvest> entry) {
		entry.stream().forEach(a -> harvestRepo.save(a));
	}

	@RequestMapping(path = "/deleteHarvest/{id}", method = RequestMethod.DELETE)
	public void deleteharvest(@PathVariable("id") String id) {
		final Harvest feed = harvestRepo.findOne(id);
		harvestRepo.delete(feed);
	}

	@RequestMapping(path = "/getHarvestList", method = RequestMethod.GET)
	public List<Harvest> getHarvestList() {
		return harvestRepo.findAll();
	}

	// Pond Operations

	@RequestMapping(path = "/getPondList", method = RequestMethod.GET)
	public List<Pond> getPondList() {
		return pondRepo.findAll();
	}

	@RequestMapping(path = "/getStockList", method = RequestMethod.GET)
	public List<Stock> getStockList() {
		return stockRepo.findAll();
	}

}
