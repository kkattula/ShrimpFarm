package com.chaty.shrimpfarm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "supplement")
public class Supplement {

	@Id
	private String _id;
	
	private String pond;

	private String date;

	private String season;

	private String type;

	private float amount;

	private Boolean planned;

	private String time;
	
	private String site;

}
