package com.jimtough.mmm.data.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class IT_LanguageSpecificHelloRepository {

	@Autowired
	LanguageSpecificHelloRepository repo;

	@Test
	void findOneByKey() {
		assertFalse(repo.findOneByKey("xxxxxxxx").isPresent());
	}

}
