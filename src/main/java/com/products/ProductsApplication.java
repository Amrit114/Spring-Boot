package com.products;




//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;

@OpenAPIDefinition(
		info = @Info(
				title = "Product Service REST API documentation",
				description = "Product Service REST API",
				version = "V1",
				contact = @Contact(
						name = "Amrit Raj Sing",
						email = "amritrajsingh1964@gmail.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				 description = "sharepoint URL Product Service API",
				 url = "example.com"
		)
		
)
@Slf4j
@SpringBootApplication
public class ProductsApplication {
	
//	private static final Logger log = LoggerFactory.getLogger(ProductsApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
		
		String str = "test";
		log.info("Product Service started successfully! {}",str);
//		log.warn("This is a warning message");
//		log.debug("This is a debug message");
//		log.trace("This is a trace message");
//		log.error("This is a error message");
		
	}

}
