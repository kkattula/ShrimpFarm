package com.chaty.shrimpfarm.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "season")
public class Season {
	
	@Id
	private String _id;
	
	private  String farmUUID;

    private String name;
    
    private List<PondInfo> ponds;

    private String status;
    
    private LocalDate startDate;
    
    private String uuid;

}
