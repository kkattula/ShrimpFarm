package com.chaty.shrimpfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ShrimpFarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShrimpFarmApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurerAdapter() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**")
	                    .allowedOrigins("http://aquajet.cfapps.io")
	                    .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
	                    .allowedHeaders("Content-Type", "authorization")
	                    .exposedHeaders("Content-Type")
	                    .maxAge(3600);
	        }
	    };
	}
	
}
