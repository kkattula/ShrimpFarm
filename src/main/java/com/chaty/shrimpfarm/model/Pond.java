package com.chaty.shrimpfarm.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "pond")
public class Pond {
	
	@Id
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
