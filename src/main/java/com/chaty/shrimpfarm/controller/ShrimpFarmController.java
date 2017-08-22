package com.chaty.shrimpfarm.controller;

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
@RequestMapping("/farm")
public class ShrimpFarmController {

	@Autowired
	FeedEntryRepo feedEntryRepo;

	@RequestMapping(path = "/feedEntry", method = RequestMethod.POST)
	public FeedEntryModel addFeedEntry(@Valid @RequestBody FeedEntryModel entry) {
		return feedEntryRepo.save(entry);
	}
	
	
	@RequestMapping(path = "/getFeedEntry/{id}", method = RequestMethod.GET)
	public FeedEntryModel getFeedEntry(@PathVariable("id") String id) {
		return feedEntryRepo.findOne(id);
	}

}
