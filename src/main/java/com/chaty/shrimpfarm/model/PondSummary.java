package com.chaty.shrimpfarm.model;

import java.util.List;

import com.chaty.shrimpfarm.activity.model.FeedActivity;

import lombok.Data;

@Data
public class PondSummary {

	private String number;

	private String site;

	private double size;
	
	private Stock stock;

	private String season;

	private Integer progressDays;
	
	private float feedTotal;

	private List<Sampling> sampling;

	private List<Supplement> supplement;

	private List<FeedActivity> feed;
	
	private List<Harvest> harvest;

}
