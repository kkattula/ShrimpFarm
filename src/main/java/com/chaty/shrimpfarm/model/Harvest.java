package com.chaty.shrimpfarm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "harvest")
public class Harvest {

	@Id
	private String _id;

	private String pond = null;
	
	private String site = null;

	private String season = null;

	private Boolean planned = null;

	private Integer tonnage = null;
	
	private Integer count = null;
	
	private String buyer = null;

	private String date = null;

	private String comments = null;
}
