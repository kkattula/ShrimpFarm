package com.chaty.shrimpfarm.activity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "activity")
public class Activity {

	@Id
	private String _id;

	private String season;
	
	private String pondUUID;
	
	private String farmUUID;

	private PondActivity pondActivity;

}
