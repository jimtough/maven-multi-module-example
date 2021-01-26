package com.jimtough.mmm.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HelloFactoryImplTest {

	private HelloFactoryImpl helloFactory;

	@BeforeEach
	void beforeEach() {
		helloFactory = new HelloFactoryImpl();
	}

	@Test
	void testGetHello() {
		assertEquals("Hello", helloFactory.getHello());
	}

}
