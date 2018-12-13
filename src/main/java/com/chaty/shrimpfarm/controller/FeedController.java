package com.chaty.shrimpfarm.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chaty.shrimpfarm.model.Feed;
import com.chaty.shrimpfarm.repository.FeedRepo;

@RestController
@RequestMapping("/farm/api/feed")
public class FeedController {

	@Autowired
	FeedRepo feedEntryRepo;


	@RequestMapping(method = RequestMethod.POST)
	public Feed addFeedEntry(@Valid @RequestBody Feed entry) {
		return feedEntryRepo.save(entry);
	}

	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public void addFeedEntryList(@Valid @RequestBody List<Feed> entry) {
		entry.stream().forEach(a -> feedEntryRepo.save(a));
	}


	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteFeedEntry(@PathVariable("id") String id) {
		final Feed feed = feedEntryRepo.findOne(id);
		feedEntryRepo.delete(feed);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Feed> getFeedList(@QueryParam("pond") String pond) {
		if (pond != null & pond != "") {
			return feedEntryRepo.findAll().stream().filter(a -> a.getPond().equals(pond)).collect(Collectors.toList());
		} else {
			return feedEntryRepo.findAll();
		}
	}

}
