package com.chaty.shrimpfarm.farmloader;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "state")
public class State {

	@Id
	private String _id;

	private String name;

	private List<District> districts;

}
