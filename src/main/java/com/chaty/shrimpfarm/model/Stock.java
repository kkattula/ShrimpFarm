package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Stock {

	private LocalDate date;

	private String hatchery;

	private Integer amount;

	private Integer plSize;

	private Integer salinity;

	private String site;

}
