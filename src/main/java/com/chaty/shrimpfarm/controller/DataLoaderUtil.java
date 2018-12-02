package com.chaty.shrimpfarm.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chaty.shrimpfarm.model.Feed;
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

	private static List<String> feedList = Arrays.asList("1", "2", "3S", "3P", "4S");
	private static List<String> seasonList = Arrays.asList("Winter", "Summer", "Rainy");
	private static List<Integer> typicalFeedAmount = Arrays.asList(5, 10, 15, 20, 25, 30);
	private static List<String> feedTime = Arrays.asList("Morning", "Afternoon", "Evening", "Night");
	private static List<String> siteList = Arrays.asList("Magani", "Thotamala", "Doruvukatta", "Thippa");

	public List<Feed> loadFeed() {

		Random rand = new Random();
		List<Feed> feedLoadList = new ArrayList<>();
		

		for (Integer i = 1; i <= 5; i++) {
			for (Integer j = 1; j <= 30; j++) {
				for (Integer k = 0; k < feedTime.size(); k++) {
					Feed obj = new Feed();
					obj.setPond(i.toString());
					obj.setTime(feedTime.get(k));
					obj.setType(feedList.get(rand.ints(1,0,5).sum()));
					obj.setAmount(typicalFeedAmount.get(rand.ints(1,0,5).sum()));
					obj.setSite("Magani");
					obj.setDate(LocalDate.now().withDayOfMonth(j));
					obj.setSeason("Winter");
					obj.setCheck(false);
					feedLoadList.add(obj);
				}
			}
		}
		
		feedLoadList.stream().forEach(a -> feedEntryRepo.save(a));
		
		return feedLoadList;

	}

}
