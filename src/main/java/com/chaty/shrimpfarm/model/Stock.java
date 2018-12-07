package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "stock")
public class Stock {

	@Id
	private String _id;
	
	private String pond;

	private LocalDate date;

	private String season;

	private String hatchery;

	private Integer amount;

	private Integer plSize;

	private Integer salinity;
	
	private String site;

}
