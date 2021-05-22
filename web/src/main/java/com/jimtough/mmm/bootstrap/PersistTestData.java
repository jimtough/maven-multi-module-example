package com.jimtough.mmm.bootstrap;

import javax.transaction.Transactional;
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

	@Transactional
	@Override
	public void run(final String... args) throws Exception {
		//-------------------------------------------------------------------------------
		LanguageSpecificHello english = LanguageSpecificHello.builder().key("en").value("Hello").build();
		LanguageSpecificHello french = LanguageSpecificHello.builder().key("fr").value("Bonjour").build();
		languageSpecificHelloRepository.saveAll(List.of(english, french));
		log.info("English and French 'hello' entities persisted to database");
		//-------------------------------------------------------------------------------

		//-------------------------------------------------------------------------------
		// These entities are added to the database via 'data-h2.sql' file
		Color red   = colorRepository.findOneByColorName(ColorName.RED).get();
		Color green = colorRepository.findOneByColorName(ColorName.GREEN).get();
		Color blue  = colorRepository.findOneByColorName(ColorName.BLUE).get();
		//-------------------------------------------------------------------------------

		//-------------------------------------------------------------------------------
		// SiteVisitor 'Jim'
		SiteVisitor jim = SiteVisitor.builder()
				.nickname("Jim")
				.uppercaseNickname("Jim".toUpperCase())
		        .favoriteColors(Set.of(blue, green))
				.build();
		siteVisitorRepository.saveAll(List.of(jim));
		SiteVisit jimVisitA = SiteVisit.builder().siteVisitor(jim).remoteAddress("0.1.2.3").build();
		siteVisitRepository.save(jimVisitA);
		TimeUnit.MILLISECONDS.sleep(1);
		SiteVisit jimVisitB = SiteVisit.builder().siteVisitor(jim).remoteAddress("0.1.2.3").build();
		siteVisitRepository.save(jimVisitB);
		// SiteVisitor 'RedGreen'
		SiteVisitor redGreen = SiteVisitor.builder()
		                             .nickname("RedGreen")
		                             .uppercaseNickname("RedGreen".toUpperCase())
		                             .favoriteColors(Set.of(red, green))
		                             .build();
		siteVisitorRepository.saveAll(List.of(redGreen));
		SiteVisit redgreenVisitA = SiteVisit.builder().siteVisitor(redGreen).remoteAddress("0.1.2.3").build();
		siteVisitRepository.save(redgreenVisitA);
		// SiteVisitor 'MrBlue'
		SiteVisitor mrBlue = SiteVisitor.builder()
		                                  .nickname("MrBlue")
		                                  .uppercaseNickname("MrBlue".toUpperCase())
		                                  .favoriteColors(Set.of(blue))
		                                  .build();
		siteVisitorRepository.saveAll(List.of(mrBlue));
		SiteVisit mrBlueVisitA = SiteVisit.builder().siteVisitor(mrBlue).remoteAddress("0.1.2.3").build();
		siteVisitRepository.save(mrBlueVisitA);
		log.info("All {} entities persisted to database", SiteVisitor.class.getSimpleName());
		//-------------------------------------------------------------------------------
	}

}
