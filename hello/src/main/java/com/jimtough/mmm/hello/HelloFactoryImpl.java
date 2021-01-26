package com.jimtough.mmm.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class HelloFactoryImpl implements HelloFactory {

	@Override
	public String getHello() {
		log.debug("Someone wants a 'hello'");
		return "Hello";
	}

}
