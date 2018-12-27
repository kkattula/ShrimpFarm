package com.chaty.shrimpfarm.model;

import java.util.List;

import lombok.Data;

@Data
public class PondSummary {
	
	private String _id;

	private Integer number;

	private String site;

	private double size;
	
	private Stock stock;

	private String season;

	private Integer progressDays;
	
	private float feedTotal;

	private List<Sampling> sampling;

	private List<Supplement> supplement;

	private List<Feed> feed;
	
	private List<Harvest> harvest;

}
