package com.chaty.shrimpfarm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chaty.shrimpfarm.model.Season;
import com.chaty.shrimpfarm.repository.SeasonRepo;

@RestController
@RequestMapping("/farm/api/season")
public class SeasonController {

	@Autowired
	SeasonRepo seasonRepo;

	@RequestMapping(method = RequestMethod.POST)
	public Season addSeasonEntry(@Valid @RequestBody Season entry) {
		return seasonRepo.save(entry);
	}

	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public void addSeasonList(@Valid @RequestBody List<Season> entry) {
		entry.stream().forEach(a -> seasonRepo.save(a));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteSeason(@PathVariable("id") String id) {
		final Season feed = seasonRepo.findOne(id);
		seasonRepo.delete(feed);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Season> getSeasonList() {
		return seasonRepo.findAll();
	}

}
