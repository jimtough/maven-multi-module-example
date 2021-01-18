package com.jimtough.mmm.config;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AppConfiguration {

	@PostConstruct
	void postConstruct() {
		log.info("postConstruct() | INVOKED");
	}

}
