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

	private String pond = null;

	private String site = null;

	private String season = null;

	private String planned = null;

	private Double tonnage = null;

	private Integer count = null;

	private String buyer = null;

	private LocalDate date = null;

	private String comments = null;

	private Double countPrice = null;
}
