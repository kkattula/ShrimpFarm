package com.chaty.shrimpfarm.model;

import java.util.List;

import lombok.Data;

@Data
public class PondActivity {
	
	private String pondUUID;
	
	private List<FeedAct> feedList;
	
	private List<Supplement> supplementList;
	
	private List<Sampling> samplingList;
	
	private Stock stock;
	
	private Harvest harvest;
	

}

