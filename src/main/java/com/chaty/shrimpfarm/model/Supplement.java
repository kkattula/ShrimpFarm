package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "supplement")
public class Supplement {

	@Id
	private String _id;
	
	private String pond;

	private LocalDate date;

	private String season;

	private String type;

	private Double amount;

	private Boolean planned;

	private String time;
	
	private String site;

}
