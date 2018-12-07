package com.chaty.shrimpfarm.controller.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.chaty.shrimpfarm.model.Feed;

@Component
public class FeedChart {

	private static List<String> feedTime = Arrays.asList("Morning", "Afternoon", "Evening", "Night");

	public static List<Feed> fillFeed(String pondNumber,int stockinMills) {

		List<FeedModel> feedModelList = new ArrayList<>();

		int Intial = 400;
		String type = "";

		for (int i = 1; i <= 30; i++) {

			if (i <= 5) {
				type = "CP-1";
				Intial += 100;
			} else if (i > 5 && i <= 10) {
				type = "CP-1/2";
				Intial += 200;
			} else if (i > 10 && i <= 20) {
				type = "CP-2";
				Intial += 200;
			} else if (i > 20 && i <= 25) {
				type = "CP-2/3";
				Intial += 200;
			} else {
				type = "CP-3";
				Intial += 200;
			}
			FeedModel feedModel = new FeedModel(i, type, Intial);
			feedModelList.add(feedModel);
		}

		List<Feed> feedList = new ArrayList<>();

		for (int i = 0; i <= 29; i++) {
			for (int j = 0; j < feedTime.size(); j++) {
				Feed feed = new Feed();
				feed.setPond("1");
				float f = feedModelList.get(i).getAmount() * stockinMills;
				feed.setAmount(f / 1000);
				feed.setType(feedModelList.get(i).getFeedType());
				feed.setTime(feedTime.get(j));
				feed.setPond(pondNumber);
				feed.setDate(LocalDate.now().withMonth(1).withDayOfMonth(i + 1));
				feed.setCheck(false);
				feed.setSeason("Summer");
				feed.setSite("Doruvukatta");
				feedList.add(feed);
			}
		}

		return feedList;

	}
}
