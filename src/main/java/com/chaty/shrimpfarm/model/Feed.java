package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "feed")
public class Feed {

	@Id
	private String _id;
	
	private String pond;

	private LocalDate date;

	private String season;

	private String type;

	private float amount;

	private boolean check;

	private String time;
	
	private String site;
	
	

}
