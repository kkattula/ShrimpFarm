package com.chaty.shrimpfarm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "sampling")
public class Sampling {

	@Id
	private String _id;
	
	private String pond;
	
	private String site;

	private String date;

	private String season;

	private String count;
	

}
