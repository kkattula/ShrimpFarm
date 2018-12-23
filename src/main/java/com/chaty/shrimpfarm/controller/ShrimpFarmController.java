package com.chaty.shrimpfarm.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chaty.shrimpfarm.controller.utils.DataLoaderUtil;
import com.chaty.shrimpfarm.model.Farm;
import com.chaty.shrimpfarm.model.Feed;
import com.chaty.shrimpfarm.model.Harvest;
import com.chaty.shrimpfarm.model.Pond;
import com.chaty.shrimpfarm.model.Sampling;
import com.chaty.shrimpfarm.model.Stock;
import com.chaty.shrimpfarm.model.Supplement;
import com.chaty.shrimpfarm.model.User;
import com.chaty.shrimpfarm.repository.ExpenseRepo;
import com.chaty.shrimpfarm.repository.FarmRepo;
import com.chaty.shrimpfarm.repository.FeedRepo;
import com.chaty.shrimpfarm.repository.HarvestRepo;
import com.chaty.shrimpfarm.repository.PondRepo;
import com.chaty.shrimpfarm.repository.SamplingRepo;
import com.chaty.shrimpfarm.repository.StockRepo;
import com.chaty.shrimpfarm.repository.SupplementRepo;
import com.chaty.shrimpfarm.repository.UserRepo;

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
	UserRepo userRepo;

	@Autowired
	FarmRepo farmRepo;

	@Autowired
	DataLoaderUtil util;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping(path = "/createUser", method = RequestMethod.POST)
	public User user(@Valid @RequestBody User user) {
		return userRepo.save(user);
	}

	@RequestMapping(path = "/userData/{query}", method = RequestMethod.GET)
	public User userData(@PathVariable("query") String query) {
		query = new String(Base64.getDecoder().decode(query));
		return userRepo.findByEmail(query);
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
	public List<Pond> getDashboard(@PathVariable("site") String site, @PathVariable("season") String season) {

		List<Pond> pondInfoList = new ArrayList<>();

		List<Pond> pondList = pondRepo.findAll();
		List<Feed> feedList = feedEntryRepo.findAll();
		List<Stock> stockList = stockRepo.findAll();
		List<Sampling> samplingList = samplingRepo.findAll();
		List<Supplement> supplementList = supplementRepo.findAll();
		List<Harvest> harvestList = harvestRepo.findAll();

		pondInfoList = pondList.stream()
				.filter(pond -> pond.getSite().equalsIgnoreCase(site) && pond.getSeason().equalsIgnoreCase(season))
				.map(obj -> {

					Pond info = new Pond();

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

					Long days = ChronoUnit.DAYS.between(info.getStock().getDate(), LocalDate.now());
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

	@RequestMapping(path = "/loadFeed/{pond}/{stock}/{site}/{season}", method = RequestMethod.GET)
	public List<Feed> loadFeed(@PathVariable("pond") String pond, @PathVariable("stock") int stock,
			@PathVariable("site") String site, @PathVariable("season") String season) {
		return util.loadFeed(pond, stock, site, season);
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

	// Pond Operations

	@RequestMapping(path = "/pond/list", method = RequestMethod.GET)
	public List<Pond> getPondList() {
		return pondRepo.findAll();
	}

	@RequestMapping(path = "/pond/list", method = RequestMethod.POST)
	public List<Pond> addPondList(@Valid @RequestBody List<Pond> pondList) {
		List<Pond> addedList = pondList.stream().map(a -> pondRepo.save(a)).collect(Collectors.toList());
		return addedList;
	}

	// Farm Operations

	@RequestMapping(path = "/farm/list", method = RequestMethod.GET)
	public List<Farm> getFarmList() {
		return farmRepo.findAll();
	}

	@RequestMapping(path = "/farm/list", method = RequestMethod.POST)
	public List<Farm> getFarmList(@Valid @RequestBody List<Farm> farmList) {
		List<Farm> addedList = farmList.stream().map(a -> farmRepo.save(a)).collect(Collectors.toList());
		return addedList;
	}

}
