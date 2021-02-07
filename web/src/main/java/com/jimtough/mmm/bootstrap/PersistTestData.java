package com.jimtough.mmm.bootstrap;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.jimtough.mmm.data.jpa.entity.*;
import com.jimtough.mmm.data.repository.ColorRepository;
import com.jimtough.mmm.data.repository.LanguageSpecificHelloRepository;
import com.jimtough.mmm.data.repository.SiteVisitRepository;
import com.jimtough.mmm.data.repository.SiteVisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PersistTestData implements CommandLineRunner {

	private final LanguageSpecificHelloRepository languageSpecificHelloRepository;
	private final ColorRepository colorRepository;
	private final SiteVisitorRepository siteVisitorRepository;
	private final SiteVisitRepository siteVisitRepository;

	@Override public void run(final String... args) throws Exception {
		LanguageSpecificHello english = LanguageSpecificHello.builder().key("en").value("Hello").build();
		LanguageSpecificHello french = LanguageSpecificHello.builder().key("fr").value("Bonjour").build();
		languageSpecificHelloRepository.saveAll(List.of(english, french));
		log.info("English and French 'hello' entities persisted to database");

		// These entities are added to the database via 'data-h2.sql' file
		Color red   = colorRepository.findOneByColorName(ColorName.RED).get();
		Color green = colorRepository.findOneByColorName(ColorName.GREEN).get();
		Color blue  = colorRepository.findOneByColorName(ColorName.BLUE).get();

		SiteVisitor jim = SiteVisitor.builder()
				.nickname("Jim")
				.uppercaseNickname("Jim".toUpperCase())
		        .favoriteColors(Set.of(blue, green))
				.build();
		siteVisitorRepository.saveAll(List.of(jim));
		SiteVisit jimVisitA = SiteVisit.builder().siteVisitor(jim).build();
		siteVisitRepository.save(jimVisitA);
		TimeUnit.MILLISECONDS.sleep(999);
		SiteVisit jimVisitB = SiteVisit.builder().siteVisitor(jim).build();
		siteVisitRepository.save(jimVisitB);

		log.info("All {} entities persisted to database", SiteVisitor.class.getSimpleName());
	}

}
