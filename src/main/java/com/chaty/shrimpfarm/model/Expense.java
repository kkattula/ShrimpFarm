package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "expense")
public class Expense {

	@Id
	private String _id;

	private LocalDate date;

	private String category;

	private String isPlanned;
	
	private String paymentType;

	private String quantity;

	private float price;

	private String comments;
}
