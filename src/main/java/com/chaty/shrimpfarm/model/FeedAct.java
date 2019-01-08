package com.chaty.shrimpfarm.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class FeedAct {
	
	private LocalDate date;
	
	private List<Feed> feedList;

}
