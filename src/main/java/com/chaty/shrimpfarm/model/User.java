package com.chaty.shrimpfarm.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "user")
public class User {
	@Id
	public String _id;

	public String username;
	
	public String token;
	
	public LocalDate dateCreated;
	
	public String email;
	
	public String image;

	public String nickname;

	public String status;

	public List<String> roles;
	
	public List<FarmInfo> farms;

}
