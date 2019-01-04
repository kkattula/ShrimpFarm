package com.chaty.shrimpfarm.farmloader;

import java.util.Set;

import lombok.Data;

@Data
public class District {
	
	private String name;
	
	private Set<Mandal> mandals;

}
