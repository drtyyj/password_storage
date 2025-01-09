package com.example.password_storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PasswordStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordStorageApplication.class, args);
	}

}
