package com.shatbha_shop.shatbha_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
@EnableAspectJAutoProxy
public class ShatbhaShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShatbhaShopApplication.class, args);
	}

}


