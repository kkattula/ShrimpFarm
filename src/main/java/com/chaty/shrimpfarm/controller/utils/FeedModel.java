package com.chaty.shrimpfarm.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedModel {

	private Integer day;
	private String feedType;
	private Integer amount;

}
