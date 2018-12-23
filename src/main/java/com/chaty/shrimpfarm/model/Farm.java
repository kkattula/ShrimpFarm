package com.chaty.shrimpfarm.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "farm")
public class Farm {

	@Id
	private String _id;

	private String name;

	private List<Pond> pondList;

	private Integer aerators;

	private Integer generators;

	private Integer currentStock;

	private Integer expWeight;

}
