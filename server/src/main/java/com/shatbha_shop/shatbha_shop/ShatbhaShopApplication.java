package com.shatbha_shop.shatbha_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class ShatbhaShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShatbhaShopApplication.class, args);
	}

}
