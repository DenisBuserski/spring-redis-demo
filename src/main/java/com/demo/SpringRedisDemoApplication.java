package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching //  Tell the Spring Container that we need a caching feature in our application
public class SpringRedisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisDemoApplication.class, args);
	}

}
