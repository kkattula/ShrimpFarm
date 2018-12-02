package com.chaty.shrimpfarm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chaty.shrimpfarm.model.Feed;
import com.chaty.shrimpfarm.repository.FeedRepo;

@RestController
@RequestMapping("/farm/api")
public class ShrimpFarmController {

	@Autowired
	FeedRepo feedEntryRepo;
	
	@Autowired
	DataLoaderUtil util;
	
	
	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public List<Feed> testLoad() {
		return util.loadFeed();
	}

	@RequestMapping(path = "/feedEntry", method = RequestMethod.POST)
	public Feed addFeedEntry(@Valid @RequestBody Feed entry) {
		return feedEntryRepo.save(entry);
	}
	
	@RequestMapping(path = "/feedEntryList", method = RequestMethod.POST)
	public void addFeedEntryList(@Valid @RequestBody List<Feed> entry) {
		entry.stream().forEach(a -> feedEntryRepo.save(a));
	}
	
	@RequestMapping(path = "/feedEntry", method = RequestMethod.PUT)
	public Feed updateFeedEntry(@Valid @RequestBody Feed entry) {
		return feedEntryRepo.insert(entry);
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
	public List<Feed> getFeedEntryList() {
		return feedEntryRepo.findAll();
	}

}
