package com.jimtough.mmm.world;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class WorldFactoryImpl implements WorldFactory {

	@Override public String getWorld() {
		log.debug("Someone wants a 'world'");
		return "World";
	}

}
