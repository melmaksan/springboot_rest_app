package com.epam.esm;

import com.epam.esm.rest_api.controllers.TagController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringbootRestAppApplicationTests {

	@Autowired
	private TagController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
