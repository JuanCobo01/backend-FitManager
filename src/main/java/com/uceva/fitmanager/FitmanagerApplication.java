package com.uceva.fitmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FitmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitmanagerApplication.class, args);
	}

}
