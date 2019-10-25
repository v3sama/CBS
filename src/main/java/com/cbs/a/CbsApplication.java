package com.cbs.a;

import com.cbs.model.Role;
import com.cbs.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@SpringBootApplication
@EnableJpaRepositories("com.cbs.repository")
@ComponentScan(basePackages = { "com.cbs.controllers", "com.cbs.services", "com.cbs.config"})
@EntityScan("com.cbs.model")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class CbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CbsApplication.class, args);
		
	}

}


