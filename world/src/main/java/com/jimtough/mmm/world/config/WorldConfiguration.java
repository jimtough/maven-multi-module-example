package com.jimtough.mmm.world.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * This @Configuration class gives an example of how to include property values from an external property source.
 * In this case, the file 'world.properties' contains an entry for the string "World".
 */
@Configuration
@PropertySource("classpath:world.properties")
public class WorldConfiguration {

	@Value("${the.word.world}")
	String theWordWorld;

	@Bean(name = "worldBean")
	public String worldBean() {
		return theWordWorld;
	}

}
