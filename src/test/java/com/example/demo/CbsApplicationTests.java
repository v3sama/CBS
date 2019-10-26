package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

@RunWith(SpringRunner.class)
@SpringBootApplication
@SpringBootTest
@EnableJpaRepositories("com.cbs.repository")
@ComponentScan(basePackages = { "com.cbs.controllers", "com.cbs.services"})
@EntityScan("com.cbs.model")   
@RestController
@EnableGlobalMethodSecurity(securedEnabled = false)
public class CbsApplicationTests {
	
	@Test
	public void contextLoads() {
	}
	
	
}
