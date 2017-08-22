package com.chaty.shrimpfarm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "feedEntry")
public class FeedEntryModel {

	@Id
	private String id;
	
	private String pondNumber;

	private String date;

	private String season;

	private String feedType;

	private float feedAmount;

	private String feedCheck;

	private String feedTime;

}
