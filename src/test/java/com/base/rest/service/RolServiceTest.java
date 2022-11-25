package com.base.rest.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;

import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.service.interfaces.RolService;

@SpringBootTest
//@TestPropertySource(
//		  locations = "classpath:application.properties")
class RolServiceTest {
	
	@Autowired
	private RolService service;
	
	@BeforeAll
	static void setup() {
	    System.out.println("@BeforeAll - executes once before all test methods in this class");
	}

	@Test
	void test() {
		ResultTableDTO result = service.findAll();
		assertTrue(result == null || result.getList().size() >= 2);
	}

}
