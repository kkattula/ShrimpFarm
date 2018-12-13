package com.chaty.shrimpfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(scanBasePackages = "com.chaty.shrimpfarm")
@EnableMongoRepositories("com.chaty.shrimpfarm.repository")
public class ShrimpFarmApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ShrimpFarmApplication.class, args);
	}

}
