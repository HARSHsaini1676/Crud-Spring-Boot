package com.harsh.crudspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class CrudSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringBootApplication.class, args);
	}

}
