package com.chaty.shrimpfarm.controller.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chaty.shrimpfarm.model.Feed;
import com.chaty.shrimpfarm.model.Harvest;
import com.chaty.shrimpfarm.model.Pond;
import com.chaty.shrimpfarm.model.Sampling;
import com.chaty.shrimpfarm.model.Stock;
import com.chaty.shrimpfarm.model.Supplement;
import com.chaty.shrimpfarm.repository.ExpenseRepo;
import com.chaty.shrimpfarm.repository.FeedRepo;
import com.chaty.shrimpfarm.repository.HarvestRepo;
import com.chaty.shrimpfarm.repository.PondRepo;
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
	
	@Autowired
	PondRepo pondRepo;

	//private static List<String> feedList = Arrays.asList("1", "2", "3S", "3P", "4S");
	//private static List<String> seasonList = Arrays.asList("Winter", "Summer", "Rainy");
	//private static List<Integer> typicalFeedAmount = Arrays.asList(5, 10, 15, 20, 25, 30);
	//private static List<String> feedTime = Arrays.asList("Morning", "Afternoon", "Evening", "Night");
	//private static List<String> siteList = Arrays.asList("Magani", "Thotamala", "Doruvukatta", "Thippa");

	
	public List<Pond> loadPond(String site,int num){
		
		List<Pond> pondList = new ArrayList<>();

		for (Integer i = 1; i <= num; i++) {
			Pond pond = new Pond();
			pond.setSite(site);
			pond.setNumber(i);
			pond.setSeason("Summer");
			pondList.add(pond);
		}

		pondList.stream().forEach(a -> pondRepo.save(a));

		return pondList;
		
	}
	
	public List<Feed> loadFeed(String pond, int stock,String site,String season) {

		List<Feed> feedLoadList = FeedChart.fillFeed(pond, stock,site,season);
		feedLoadList.stream().forEach(a -> feedEntryRepo.save(a));
		return feedLoadList;

	}
	
	
	public List<Supplement> loadSupplement(int num) {

		List<Supplement> samplingList = new ArrayList<>();

		for (Integer i = 1; i <= num; i++) {
			Supplement supplement = new Supplement();
			supplement.setSite("Doruvukatta");
			supplement.setPond(i.toString());
			supplement.setAmount(25.00);
			supplement.setDate(LocalDate.now().withYear(2017).withMonth(3).withDayOfMonth(6));
			supplement.setSeason("Summer");
			samplingList.add(supplement);
		}

		samplingList.stream().forEach(a -> supplementRepo.save(a));

		return samplingList;

	}
	
	
	public List<Sampling> loadSampling(int num) {

		List<Sampling> samplingList = new ArrayList<>();

		for (Integer i = 1; i <= num; i++) {
			Sampling sampling = new Sampling();
			sampling.setSite("Doruvukatta");
			sampling.setPond("1");
			sampling.setCount("150");
			sampling.setDate(LocalDate.now().withYear(2017).withMonth(3).withDayOfMonth(6));
			sampling.setSeason("Summer");
			sampling.setTime("Morning");
			samplingList.add(sampling);
		}

		samplingList.stream().forEach(a -> samplingRepo.save(a));

		return samplingList;

	}

	public List<Stock> loadStock(int num) {

		List<Stock> stockList = new ArrayList<>();

		for (Integer i = 1; i <= num; i++) {
			Stock stock = new Stock();
			stock.setSite("Doruvukatta");
			stock.setPond(i);
			stock.setAmount(250000);
			stock.setDate(LocalDate.now().withYear(2018).withMonth(11).withDayOfMonth(6));
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
			harvest.setPlanned("YES");
			harvest.setSeason("Summer");
			harvest.setCountPrice(230d);
			harvest.setComments("Info about harvest goes here");
			harvestList.add(harvest);
		}

		harvestList.stream().forEach(a -> harvestRepo.save(a));

		return harvestList;

	}

}
