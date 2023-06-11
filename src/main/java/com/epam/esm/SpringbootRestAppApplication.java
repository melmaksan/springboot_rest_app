package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.COLLECTION_JSON)
public class SpringbootRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestAppApplication.class, args);
	}

}
