package com.chaty.shrimpfarm.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chaty.shrimpfarm.controller.utils.FeedChart;
import com.chaty.shrimpfarm.model.Feed;
import com.chaty.shrimpfarm.model.Harvest;
import com.chaty.shrimpfarm.model.Stock;
import com.chaty.shrimpfarm.repository.ExpenseRepo;
import com.chaty.shrimpfarm.repository.FeedRepo;
import com.chaty.shrimpfarm.repository.HarvestRepo;
import com.chaty.shrimpfarm.repository.SamplingRepo;
import com.chaty.shrimpfarm.repository.StockRepo;
import com.chaty.shrimpfarm.repository.SupplementRepo;

@Component
public class DataLoaderUtil {

	@Autowired
	ExpenseRepo expenseRepo;

	@Autowired
	HarvestRepo harvestRepo;

	@Autowired
	FeedRepo feedEntryRepo;

	@Autowired
	StockRepo stockRepo;

	@Autowired
	SupplementRepo supplementRepo;

	@Autowired
	SamplingRepo samplingRepo;

	//private static List<String> feedList = Arrays.asList("1", "2", "3S", "3P", "4S");
	//private static List<String> seasonList = Arrays.asList("Winter", "Summer", "Rainy");
	//private static List<Integer> typicalFeedAmount = Arrays.asList(5, 10, 15, 20, 25, 30);
	//private static List<String> feedTime = Arrays.asList("Morning", "Afternoon", "Evening", "Night");
	//private static List<String> siteList = Arrays.asList("Magani", "Thotamala", "Doruvukatta", "Thippa");

	public List<Feed> loadFeed(String pond, int stock) {

		List<Feed> feedLoadList = FeedChart.fillFeed(pond, stock);
		feedLoadList.stream().forEach(a -> feedEntryRepo.save(a));
		return feedLoadList;

	}

	public List<Stock> loadStock(int num) {

		List<Stock> stockList = new ArrayList<>();

		for (Integer i = 1; i <= num; i++) {
			Stock stock = new Stock();
			stock.setSite("Doruvukatta");
			stock.setPond("1");
			stock.setAmount(250000);
			stock.setDate(LocalDate.now().withYear(2017).withMonth(3).withDayOfMonth(6));
			stock.setHatchery("Mahalakshmi");
			stock.setSalinity(22);
			stock.setPlSize(18);
			stock.setSeason("Summer");
			stockList.add(stock);
		}

		stockList.stream().forEach(a -> stockRepo.save(a));

		return stockList;

	}

	public List<Harvest> loadHarvest(int num) {

		List<Harvest> harvestList = new ArrayList<>();

		for (Integer i = 1; i <= num; i++) {
			Harvest harvest = new Harvest();
			harvest.setSite("Doruvukatta");
			harvest.setPond(i.toString());
			harvest.setBuyer("Ashwini");
			harvest.setDate(LocalDate.now().withYear(2017).withMonth(3).withDayOfMonth(6));
			harvest.setCount(65);
			harvest.setTonnage(2800d);
			harvest.setPlanned(true);
			harvest.setSeason("Summer");
			harvest.setCountPrice(230d);
			harvest.setComments("Info about harvest goes here");
			harvestList.add(harvest);
		}

		harvestList.stream().forEach(a -> harvestRepo.save(a));

		return harvestList;

	}

}
