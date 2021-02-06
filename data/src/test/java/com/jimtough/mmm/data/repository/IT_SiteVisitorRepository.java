package com.jimtough.mmm.data.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.jimtough.mmm.data.jpa.entity.SiteVisitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class IT_SiteVisitorRepository {

	@Autowired
	SiteVisitorRepository repo;

	@Test
	void findOneByName_ThenSave_ThenFindOneByName() {
		assertFalse(repo.findOneByUppercaseNickname("PLOP").isPresent());

		repo.save(SiteVisitor.builder().nickname("plop").uppercaseNickname("PLOP").build());

		assertTrue(repo.findOneByUppercaseNickname("PLOP").isPresent());
	}

}
