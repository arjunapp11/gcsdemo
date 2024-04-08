package com.gcpfile.gcpdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class GcpdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcpdemoApplication.class, args);
		
		System.out.println("GCP Server is up......");
	}

}
