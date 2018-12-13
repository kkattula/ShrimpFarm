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

import com.chaty.shrimpfarm.model.Supplement;
import com.chaty.shrimpfarm.repository.SupplementRepo;

@RestController
@RequestMapping("/farm/api/supplement")
public class SupplementController {

	@Autowired
	SupplementRepo supplementRepo;

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Supplement addSupplementEntry(@Valid @RequestBody Supplement entry) {
		return supplementRepo.save(entry);
	}

	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public void addSupplementList(@Valid @RequestBody List<Supplement> entry) {
		entry.stream().forEach(a -> supplementRepo.save(a));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteSupplement(@PathVariable("id") String id) {
		final Supplement feed = supplementRepo.findOne(id);
		supplementRepo.delete(feed);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
		public List<Supplement> getSupplementList(String pond) {
			if (pond != null & pond != "") {
				return supplementRepo.findAll().stream().filter(a -> a.getPond().equals(pond)).collect(Collectors.toList());
			} else {
				return supplementRepo.findAll();
			}
		}

}
