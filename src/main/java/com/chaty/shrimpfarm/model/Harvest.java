package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Harvest {

	private String planned;

	private Double tonnage;

	private Integer count;

	private String buyer;

	private LocalDate date;

	private String comments;

	private Double countPrice;
}
