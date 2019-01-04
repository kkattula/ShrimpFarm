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
import com.chaty.shrimpfarm.farmloader.CAAFarm;
import com.chaty.shrimpfarm.farmloader.CAAFarmLoader;
import com.chaty.shrimpfarm.farmloader.State;
import com.chaty.shrimpfarm.model.Farm;
import com.chaty.shrimpfarm.model.Feed;
import com.chaty.shrimpfarm.model.Harvest;
import com.chaty.shrimpfarm.model.Pond;
import com.chaty.shrimpfarm.model.PondSummary;
import com.chaty.shrimpfarm.model.Sampling;
import com.chaty.shrimpfarm.model.Season;
import com.chaty.shrimpfarm.model.Stock;
import com.chaty.shrimpfarm.model.Supplement;
import com.chaty.shrimpfarm.model.User;
import com.chaty.shrimpfarm.repository.CAAFarmRepo;
import com.chaty.shrimpfarm.repository.ExpenseRepo;
import com.chaty.shrimpfarm.repository.FarmRepo;
import com.chaty.shrimpfarm.repository.FeedRepo;
import com.chaty.shrimpfarm.repository.HarvestRepo;
import com.chaty.shrimpfarm.repository.PondRepo;
import com.chaty.shrimpfarm.repository.SamplingRepo;
import com.chaty.shrimpfarm.repository.SeasonRepo;
import com.chaty.shrimpfarm.repository.StateRepo;
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
	SeasonRepo seasonRepo;
	
	@Autowired
	StateRepo stateRepo;
	
	@Autowired
	CAAFarmRepo caaFarmRepo;

	@Autowired
	DataLoaderUtil util;
	
	@Autowired
	CAAFarmLoader caaUtil;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
	
//	@RequestMapping("/loadCAA")
//	public String loadCAA() {
//		caaUtil.loadCAAFarms();
//		return "DONE";
//	}
	

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
	public List<PondSummary> getDashboard(@PathVariable("site") String site, @PathVariable("season") String season) {

		List<PondSummary> pondInfoList = new ArrayList<>();

		List<Season> seasonList = seasonRepo.findByFarmUUID(site);
		List<Feed> feedList = feedEntryRepo.findAll();
		List<Stock> stockList = stockRepo.findAll();
		List<Sampling> samplingList = samplingRepo.findAll();
		List<Supplement> supplementList = supplementRepo.findAll();
		List<Harvest> harvestList = harvestRepo.findAll();

		pondInfoList = seasonList.get(0).getPonds().stream().map(obj -> {

			PondSummary info = new PondSummary();

			info.setStock(stockList.stream()
					.filter(s -> s.getSeason().equals(season) && s.getPond().equals(obj.getUuid())).findFirst().get());

			info.setFeed(
					feedList.stream().filter(s -> s.getSeason().equals(season) && s.getPond().equals(obj.getUuid()))
							.collect(Collectors.toList()));

			info.setSupplement(supplementList.stream()
					.filter(s -> s.getSeason().equals(season) && s.getPond().equals(obj.getUuid()))
					.collect(Collectors.toList()));

			info.setHarvest(
					harvestList.stream().filter(s -> s.getSeason().equals(season) && s.getPond().equals(obj.getUuid()))
							.collect(Collectors.toList()));

			info.setSampling(
					samplingList.stream().filter(s -> s.getSeason().equals(season) && s.getPond().equals(obj.getUuid()))
							.collect(Collectors.toList()));

			Double total = info.getFeed().stream().mapToDouble(a -> a.getAmount()).sum();
			info.setFeedTotal(total.floatValue());

			if (info.getHarvest() != null && info.getHarvest().size() > 0) {
				LocalDate harvestDate = info.getHarvest().get(0).getDate();
				Long days = ChronoUnit.DAYS.between(info.getStock().getDate(), harvestDate);
				info.setProgressDays(days.intValue());
			} else {
				Long days = ChronoUnit.DAYS.between(info.getStock().getDate(), LocalDate.now());
				info.setProgressDays(days.intValue());
			}

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

	@RequestMapping(path = "/loadHarvest/{pond}/{site}/{season}", method = RequestMethod.GET)
	public List<Harvest> loadHarvest(@PathVariable("pond") String pond, @PathVariable("site") String site,
			@PathVariable("season") String season) {
		return util.loadHarvest(pond, site, season);
	}

	@RequestMapping(path = "/loadSupplement/{pond}/{site}/{season}", method = RequestMethod.GET)
	public List<Supplement> loadSupplement(@PathVariable("pond") String pond, @PathVariable("site") String site,
			@PathVariable("season") String season) {
		return util.loadSupplement(pond, site, season);
	}

	@RequestMapping(path = "/loadSampling/{pond}/{site}/{season}", method = RequestMethod.GET)
	public List<Sampling> loadSampling(@PathVariable("pond") String pond, @PathVariable("site") String site,
			@PathVariable("season") String season) {
		return util.loadSampling(pond, site, season);
	}

	@RequestMapping(path = "/loadStock/{pond}/{site}/{season}", method = RequestMethod.GET)
	public List<Stock> loadStock(@PathVariable("pond") String pond, @PathVariable("site") String site,
			@PathVariable("season") String season) {
		return util.loadStock(pond, site, season);
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

	@RequestMapping(path = "/caafarm/list/{mandal}", method = RequestMethod.GET)
	public List<CAAFarm> getCAAList(@PathVariable("mandal") String mandal) {
		return caaFarmRepo.getByUuid(mandal);
	}
	
	@RequestMapping(path = "/state/list", method = RequestMethod.GET)
	public List<State> getStateList() {
		return stateRepo.findAll();
	}

	@RequestMapping(path = "/farm/list", method = RequestMethod.POST)
	public List<Farm> getFarmList(@Valid @RequestBody List<Farm> farmList) {
		List<Farm> addedList = farmList.stream().map(a -> farmRepo.save(a)).collect(Collectors.toList());
		return addedList;
	}
	
	@RequestMapping(path = "/caafarm/list", method = RequestMethod.GET)
	public List<Farm> getFarmList() {
		return farmRepo.findAll();
	}

}
