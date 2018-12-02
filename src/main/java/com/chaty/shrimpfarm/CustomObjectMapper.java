package com.chaty.shrimpfarm;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Primary
@Component
public class CustomObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomObjectMapper() {
		findAndRegisterModules();
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
	}

}
