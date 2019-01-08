package com.chaty.shrimpfarm.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "activity")
public class Activity {
	
	@Id
	private String _id;
	
	private String season;
	
	private List<PondActivity> pondActivityList;

}
