package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;



@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MyappApplication {

	@Autowired

	public static void main(String[] args) {
		SpringApplication.run(MyappApplication.class, args);
	}


}
