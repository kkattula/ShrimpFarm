package com.chaty.shrimpfarm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "expense")
public class Expense {

	@Id
	private String _id;

	private String categoryName = null;

	private String subCategory = null;

	private String planned = null;

	private Integer price = null;

	private String date = null;

	private String comments = null;
}
