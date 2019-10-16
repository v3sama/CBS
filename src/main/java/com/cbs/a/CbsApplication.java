package com.cbs.a;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.cbs.repository")
@ComponentScan(basePackages = { "com.cbs.controllers", "com.cbs.services"})
@EntityScan("com.cbs.model")   
public class CbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CbsApplication.class, args);
	}

}
