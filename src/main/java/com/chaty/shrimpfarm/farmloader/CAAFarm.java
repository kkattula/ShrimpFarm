package com.chaty.shrimpfarm.farmloader;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "caafarm")
public class CAAFarm {
	
	@Id
	private String _id;
	
	private String regNo;
	
	private String name;
	
	private String fathersName;
	
	private String address;
	
	private String mandal;
	
	private String revenueVillage;
	
	private String waterSpread;
	
	private List<String> surveyList;
	
	private String issueDate;
	
	private String status;
	
	private String uuid;
	

}
