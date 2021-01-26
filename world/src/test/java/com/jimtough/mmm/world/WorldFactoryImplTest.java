package com.jimtough.mmm.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorldFactoryImplTest {

	private WorldFactoryImpl worldFactory;

	@BeforeEach
	void beforeEach() {
		worldFactory = new WorldFactoryImpl();
	}

	@Test
	void testGetWorld() {
		assertEquals("World", worldFactory.getWorld());
	}

}
