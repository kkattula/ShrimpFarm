package com.chaty.shrimpfarm.controller;

import java.security.Principal;
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
import com.chaty.shrimpfarm.repository.ExpenseRepo;
import com.chaty.shrimpfarm.repository.FeedRepo;

@RestController
@RequestMapping("/farm/api")
public class ShrimpFarmController {

	@Autowired
	FeedRepo feedEntryRepo;

	@Autowired
	ExpenseRepo expenseRepo;

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

	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public List<Feed> testLoad() {
		// return util.loadFeed();
		return new ArrayList<Feed>();
	}

	// Feed Entry Paths

	@RequestMapping(path = "/feedEntry", method = RequestMethod.POST)
	public Feed addFeedEntry(@Valid @RequestBody Feed entry) {
		return feedEntryRepo.save(entry);
	}

	@RequestMapping(path = "/addFeedEntryList", method = RequestMethod.POST)
	public void addFeedEntryList(@Valid @RequestBody List<Feed> entry) {
		entry.stream().forEach(a -> feedEntryRepo.save(a));
	}

	@RequestMapping(path = "/getFeedEntry/{id}", method = RequestMethod.GET)
	public Feed getFeedEntry(@PathVariable("id") String id) {
		return feedEntryRepo.findOne(id);
	}

	@RequestMapping(path = "/deleteFeedEntry/{id}", method = RequestMethod.DELETE)
	public void deleteFeedEntry(@PathVariable("id") String id) {
		final Feed feed = feedEntryRepo.findOne(id);
		feedEntryRepo.delete(feed);
	}

	@RequestMapping(path = "/getFeedEntryList", method = RequestMethod.GET)
	public List<Feed> getFeedEntryList(@QueryParam("pond") String pond) {
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

}
