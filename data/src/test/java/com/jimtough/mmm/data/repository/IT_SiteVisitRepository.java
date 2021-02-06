package com.jimtough.mmm.data.repository;

import static org.junit.jupiter.api.Assertions.*;

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
	void findOneByName_ThenSave_ThenFindOneByName() {
		assertTrue(visitRepo.findAll().isEmpty());

		SiteVisitor plop = visitorRepo.save(SiteVisitor.builder().nickname("plop").uppercaseNickname("PLOP").build());
		visitRepo.save(SiteVisit.builder().siteVisitor(plop).build());

		assertEquals(1, visitRepo.findAll().size());
	}

}
