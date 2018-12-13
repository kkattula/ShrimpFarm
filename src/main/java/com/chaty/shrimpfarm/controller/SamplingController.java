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

import com.chaty.shrimpfarm.model.Sampling;
import com.chaty.shrimpfarm.repository.SamplingRepo;

@RestController
@RequestMapping("/farm/api/sampling")
public class SamplingController {

	@Autowired
	SamplingRepo samplingRepo;

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Sampling addSamplingEntry(@Valid @RequestBody Sampling entry) {
		return samplingRepo.save(entry);
	}

	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public void addSamplingList(@Valid @RequestBody List<Sampling> entry) {
		entry.stream().forEach(a -> samplingRepo.save(a));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteSampling(@PathVariable("id") String id) {
		final Sampling feed = samplingRepo.findOne(id);
		samplingRepo.delete(feed);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Sampling> getSamplingList(@QueryParam("pond") String pond) {
		if (pond != null & pond != "") {
			return samplingRepo.findAll().stream().filter(a -> a.getPond().equals(pond)).collect(Collectors.toList());
		} else {
			return samplingRepo.findAll();
		}
	}

}
