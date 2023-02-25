package com.currencyconverter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Currency Converter API",
		version = "3.0", description = "First project using spring boot jpa"))
public class ConvertorBySpringJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConvertorBySpringJpaApplication.class, args);
	}
}