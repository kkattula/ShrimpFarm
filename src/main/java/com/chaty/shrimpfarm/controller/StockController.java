package com.chaty.shrimpfarm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chaty.shrimpfarm.model.Stock;
import com.chaty.shrimpfarm.repository.StockRepo;

@RestController
@RequestMapping("/farm/api/stock")
public class StockController {

	@Autowired
	StockRepo stockRepo;

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Stock addSamplingEntry(@Valid @RequestBody Stock entry) {
		return stockRepo.save(entry);
	}

	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public void addSamplingList(@Valid @RequestBody List<Stock> entry) {
		entry.stream().forEach(a -> stockRepo.save(a));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteSampling(@PathVariable("id") String id) {
		final Stock feed = stockRepo.findOne(id);
		stockRepo.delete(feed);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Stock> getStockList() {
		return stockRepo.findAll();
	}

}
