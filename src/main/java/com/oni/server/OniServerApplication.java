package com.oni.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OniServerApplication {
	
	private static Logger logger = LoggerFactory.getLogger(OniServerApplication.class);

	public static void main(String[] args) {
		
		SpringApplication.run(OniServerApplication.class, args);
		logger.info("Oni Application Started ...");

	}

}