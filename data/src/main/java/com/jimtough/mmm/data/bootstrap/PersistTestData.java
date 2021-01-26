package com.jimtough.mmm.data.bootstrap;

import java.util.List;

import com.jimtough.mmm.data.jpa.entity.LanguageSpecificHello;
import com.jimtough.mmm.data.repository.LanguageSpecificHelloRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PersistTestData implements CommandLineRunner {

	private final LanguageSpecificHelloRepository languageSpecificHelloRepository;

	@Override public void run(final String... args) throws Exception {
		LanguageSpecificHello english = LanguageSpecificHello.builder().key("en").value("Hello").build();
		LanguageSpecificHello french = LanguageSpecificHello.builder().key("fr").value("Bonjour").build();
		languageSpecificHelloRepository.saveAll(List.of(english, french));
		log.info("English and French 'hello' entities persisted to database");
	}

}
