package com.jimtough.mmm.data.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import com.jimtough.mmm.data.jpa.entity.SiteVisit;
import com.jimtough.mmm.data.jpa.entity.SiteVisitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class IT_SiteVisitRepository {

	@Autowired
	SiteVisitorRepository visitorRepo;
	@Autowired
	SiteVisitRepository visitRepo;

	@Test
	void findAll_ThenSave_ThenFindAll() {
		assertTrue(visitRepo.findAll().isEmpty());

		SiteVisitor plop = visitorRepo.save(SiteVisitor.builder().nickname("plop").uppercaseNickname("PLOP").build());
		visitRepo.save(SiteVisit.builder().siteVisitor(plop).build());

		assertEquals(1, visitRepo.findAll().size());
	}

	@Test
	void findByVisitTimestampAfter() throws InterruptedException {
		final SiteVisitor plop = visitorRepo.save(SiteVisitor.builder().nickname("plop").uppercaseNickname("PLOP").build());
		assertTrue(visitRepo.findAll().isEmpty());

		ZonedDateTime zdtNow = ZonedDateTime.now();
		visitRepo.save(SiteVisit.builder().siteVisitor(plop).build());
		TimeUnit.MILLISECONDS.sleep(1);
		visitRepo.save(SiteVisit.builder().siteVisitor(plop).build());
		TimeUnit.MILLISECONDS.sleep(1);
		visitRepo.save(SiteVisit.builder().siteVisitor(plop).build());

		assertEquals(3, visitRepo.findAll().size());
		assertEquals(3, visitRepo.findByVisitTimestampAfter(zdtNow.minusDays(999)).size());
		assertEquals(0, visitRepo.findByVisitTimestampAfter(zdtNow.plusDays(999)).size());
	}

	@Test
	void findBySiteVisitor() throws InterruptedException {
		final SiteVisitor plop = visitorRepo.save(SiteVisitor.builder().nickname("plop").uppercaseNickname("PLOP").build());
		final SiteVisitor fizz = visitorRepo.save(SiteVisitor.builder().nickname("fizz").uppercaseNickname("FIZZ").build());
		assertTrue(visitRepo.findAll().isEmpty());

		visitRepo.save(SiteVisit.builder().siteVisitor(plop).build());
		visitRepo.save(SiteVisit.builder().siteVisitor(fizz).build());
		TimeUnit.MILLISECONDS.sleep(1);
		visitRepo.save(SiteVisit.builder().siteVisitor(plop).build());
		visitRepo.save(SiteVisit.builder().siteVisitor(fizz).build());
		TimeUnit.MILLISECONDS.sleep(1);
		SiteVisit plopC = visitRepo.save(SiteVisit.builder().siteVisitor(plop).build());

		assertEquals(5, visitRepo.findAll().size());
		assertEquals(3, visitRepo.findBySiteVisitor(plop).size());
		assertEquals(2, visitRepo.findBySiteVisitor(fizz).size());
		assertTrue(visitRepo.findFirstBySiteVisitorOrderByVisitTimestampDesc(plop).isPresent());
		assertEquals(plopC, visitRepo.findFirstBySiteVisitorOrderByVisitTimestampDesc(plop).get());
	}

}
