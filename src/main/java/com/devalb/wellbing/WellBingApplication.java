package com.devalb.wellbing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WellBingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellBingApplication.class, args);
	}


}