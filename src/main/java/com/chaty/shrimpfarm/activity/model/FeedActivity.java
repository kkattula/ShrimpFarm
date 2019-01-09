package com.chaty.shrimpfarm.activity.model;

import java.time.LocalDate;
import java.util.List;

import com.chaty.shrimpfarm.model.Feed;

import lombok.Data;

@Data
public class FeedActivity {
	
	private LocalDate date;
	
	private List<Feed> feedList;

}
