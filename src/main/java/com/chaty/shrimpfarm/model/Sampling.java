package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Sampling {

	private LocalDate date;

	private String count;

	private String comments;
	
	private String pieces;

}
