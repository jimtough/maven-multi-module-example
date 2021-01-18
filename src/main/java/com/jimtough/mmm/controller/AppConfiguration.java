package com.jimtough.mmm.controller;

import com.jimtough.mmm.HelloFactory;
import com.jimtough.mmm.WorldFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

	@Bean
	HelloFactory helloFactory() {
		return ()->"Hello";
	}

	@Bean
	WorldFactory worldFactory() {
		return ()->"World";
	}

}
