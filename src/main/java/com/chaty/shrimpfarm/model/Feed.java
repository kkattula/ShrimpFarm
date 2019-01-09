package com.chaty.shrimpfarm.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Feed {

	private LocalDate date;

	private String type;

	private float amount;

	private boolean check;

	private String time;

}
