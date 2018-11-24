package com.chaty.shrimpfarm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "feedEntry")
public class FeedEntryModel {

	@Id
	private String id;
	
	private String pond;

	private String date;

	private String season;

	private String type;

	private float amount;

	private String check;

	private String time;
	
	private String site;

}
