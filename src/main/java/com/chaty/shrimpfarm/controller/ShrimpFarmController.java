package com.chaty.shrimpfarm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chaty.shrimpfarm.model.FeedEntryModel;
import com.chaty.shrimpfarm.repository.FeedEntryRepo;

@RestController
@RequestMapping("/farm/api")
public class ShrimpFarmController {

	@Autowired
	FeedEntryRepo feedEntryRepo;

	@RequestMapping(path = "/feedEntry", method = RequestMethod.POST)
	public FeedEntryModel addFeedEntry(@Valid @RequestBody FeedEntryModel entry) {
		return feedEntryRepo.save(entry);
	}
	
	@RequestMapping(path = "/feedEntry", method = RequestMethod.PUT)
	public FeedEntryModel updateFeedEntry(@Valid @RequestBody FeedEntryModel entry) {
		return feedEntryRepo.insert(entry);
	}
	
	
	@RequestMapping(path = "/getFeedEntry/{id}", method = RequestMethod.GET)
	public FeedEntryModel getFeedEntry(@PathVariable("id") String id) {
		return feedEntryRepo.findOne(id);
	}
	
	@RequestMapping(path = "/deleteFeedEntry/{id}", method = RequestMethod.DELETE)
	public void deleteFeedEntry(@PathVariable("id") String id) {
		final FeedEntryModel feed = feedEntryRepo.findOne(id);
		 feedEntryRepo.delete(feed);
	}
	
	@RequestMapping(path = "/getFeedEntryList", method = RequestMethod.GET)
	public List<FeedEntryModel> getFeedEntryList() {
		return feedEntryRepo.findAll();
	}

}
