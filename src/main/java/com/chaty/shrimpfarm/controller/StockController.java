package com.chaty.shrimpfarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Stock> getStockList() {
		return stockRepo.findAll();
	}

}
