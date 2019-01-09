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

import com.chaty.shrimpfarm.activity.model.Activity;
import com.chaty.shrimpfarm.farmloader.CAAFarm;
import com.chaty.shrimpfarm.farmloader.CAAFarmLoader;
import com.chaty.shrimpfarm.farmloader.ExistingDataLoader;
import com.chaty.shrimpfarm.farmloader.State;
import com.chaty.shrimpfarm.model.PondSummary;
import com.chaty.shrimpfarm.model.Stock;
import com.chaty.shrimpfarm.model.User;
import com.chaty.shrimpfarm.repository.ActivityRepo;
import com.chaty.shrimpfarm.repository.CAAFarmRepo;
import com.chaty.shrimpfarm.repository.ExpenseRepo;
import com.chaty.shrimpfarm.repository.StateRepo;
import com.chaty.shrimpfarm.repository.UserRepo;

@RestController
@RequestMapping("/farm/api")
public class ShrimpFarmController {

	@Autowired
	ExpenseRepo expenseRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	StateRepo stateRepo;

	@Autowired
	CAAFarmRepo caaFarmRepo;

	@Autowired
	ActivityRepo activityRepo;

	@Autowired
	CAAFarmLoader caaUtil;

	@Autowired
	ExistingDataLoader oldLoader;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	// @RequestMapping("/loadCAA")
	// public String loadCAA() {
	// caaUtil.loadCAAFarms();
	// return "DONE";
	// }

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

	@RequestMapping(path = "/loadOLD", method = RequestMethod.GET)
	public String oldData() {
		oldLoader.loadExisting();
		return "OK";
	}

	// Dashboard Data

	@RequestMapping(path = "/getDashboard/{site}/{season}", method = RequestMethod.GET)
	public List<PondSummary> getDashboard(@PathVariable("site") String site, @PathVariable("season") String season) {

		List<PondSummary> pondInfoList = new ArrayList<>();

		List<Activity> pondActivityBySeason = activityRepo.findBySeason(season);

		pondInfoList = pondActivityBySeason.stream().map(obj -> {

			PondSummary info = new PondSummary();

			Stock stok = obj.getPondActivity().getStock();

			if (stok != null) {
				info.setStock(stok);
			}

			info.setFeed(obj.getPondActivity().getFeedList());

			info.setSupplement(obj.getPondActivity().getSupplementList());

			info.setHarvest(obj.getPondActivity().getHarvest());

			info.setSampling(obj.getPondActivity().getSamplingList());

			Double total = info.getFeed().stream()
					.mapToDouble(a -> a.getFeedList().stream().mapToDouble(fe -> fe.getAmount()).sum()).sum();

			info.setFeedTotal(total.floatValue());

			if (info.getHarvest() != null && info.getHarvest().size() > 0) {
				LocalDate harvestDate = info.getHarvest().get(0).getDate();
				Long days = ChronoUnit.DAYS.between(info.getStock().getDate(), harvestDate);
				info.setProgressDays(days.intValue());
			} else {
				Long days;
				if (info.getStock() != null) {
					days = ChronoUnit.DAYS.between(info.getStock().getDate(), LocalDate.now());
				} else {
					days = Long.valueOf("0");
				}

				info.setProgressDays(days.intValue());
			}

			info.setNumber(obj.getPondUUID());
			info.setSite(obj.getFarmUUID());
			info.setSeason(obj.getSeason());
			info.setSize(1.0);

			return info;
		}).collect(Collectors.toList());

		return pondInfoList;

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

}
