package com.jimtough.mmm.hello;

import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.LanguageSpecificHello;
import com.jimtough.mmm.data.repository.LanguageSpecificHelloRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class HelloFactoryImpl implements HelloFactory {

	final static String DEFAULT_ENGLISH_HELLO = "Hello";

	private final LanguageSpecificHelloRepository languageSpecificHelloRepository;

	@Override
	public String getHello() {
		log.debug("Someone wants a 'hello'");
		Optional<LanguageSpecificHello> languageSpecificHello = languageSpecificHelloRepository.findOneByKey("en");
		if (languageSpecificHello.isPresent()) {
			return languageSpecificHello.get().getValue();
		}
		log.warn("No language-specific hello found in database for language '{}' - return default English hello", "en");
		return DEFAULT_ENGLISH_HELLO;
	}

}
