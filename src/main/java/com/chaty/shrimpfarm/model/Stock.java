package com.chaty.shrimpfarm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "feed")
public class Stock {

	@Id
	private String _id;
	
	private String pond;

	private String date;

	private String season;

	private String hatchery;

	private Integer amount;

	private String plSize;

	private Integer salinity;
	
	private String site;

}
