package com.jimtough.mmm.world;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class WorldFactoryImpl implements WorldFactory {

	private final String world;

	WorldFactoryImpl(@Qualifier("worldBean")String world) {
		this.world = Objects.requireNonNull(world);
	}

	@Override public String getWorld() {
		log.debug("Someone wants a 'world'");
		return world;
	}

}
