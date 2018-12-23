package com.chaty.shrimpfarm.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chaty.shrimpfarm.model.Harvest;
import com.chaty.shrimpfarm.repository.HarvestRepo;

@RestController
@RequestMapping("/farm/api/harvest")
public class HarvestController {
	
	@Autowired
	HarvestRepo harvestRepo;
	

	@RequestMapping(method = RequestMethod.POST)
	public Harvest addharvestEntry(@Valid @RequestBody Harvest entry) {
		return harvestRepo.save(entry);
	}

	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public void addharvestList(@Valid @RequestBody List<Harvest> entry) {
		entry.stream().forEach(a -> harvestRepo.save(a));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteharvest(@PathVariable("id") String id) {
		final Harvest feed = harvestRepo.findOne(id);
		harvestRepo.delete(feed);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Harvest> getHarvestList(String pond) {
		if (pond != null & pond != "") {
			return harvestRepo.findAll().stream().filter(a -> a.getPond().equals(pond)).collect(Collectors.toList());
		} else {
			return harvestRepo.findAll();
		}
	}


}
