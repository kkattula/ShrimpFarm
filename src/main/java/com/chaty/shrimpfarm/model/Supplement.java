package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Supplement {

	private LocalDate date;

	private String type;

	private Double amount;

	private Boolean planned;

	private String time;

	private String site;

}
