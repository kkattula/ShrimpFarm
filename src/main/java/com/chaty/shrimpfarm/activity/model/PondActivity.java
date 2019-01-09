package com.chaty.shrimpfarm.activity.model;

import java.util.List;

import com.chaty.shrimpfarm.model.Harvest;
import com.chaty.shrimpfarm.model.Sampling;
import com.chaty.shrimpfarm.model.Stock;
import com.chaty.shrimpfarm.model.Supplement;

import lombok.Data;

@Data
public class PondActivity {
	
	private List<FeedActivity> feedList;
	
	private List<Supplement> supplementList;
	
	private List<Sampling> samplingList;
	
	private Stock stock;
	
	private List<Harvest> harvest;
	

}

