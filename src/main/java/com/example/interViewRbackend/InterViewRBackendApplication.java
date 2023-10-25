package com.example.interViewRbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InterViewRBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterViewRBackendApplication.class, args);

	}

	@GetMapping
	public String Hello() {
		return "hello ma pup";
	}

}
