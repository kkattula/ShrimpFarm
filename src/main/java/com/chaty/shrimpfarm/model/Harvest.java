package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "harvest")
public class Harvest {

	@Id
	private String _id;

	private String pond;

	private String site;

	private String season;

	private String planned;

	private Double tonnage;

	private Integer count;

	private String buyer;

	private LocalDate date;

	private String comments;

	private Double countPrice;
}
