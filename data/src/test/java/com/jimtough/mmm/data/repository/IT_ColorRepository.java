package com.jimtough.mmm.data.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.jimtough.mmm.data.jpa.entity.Color;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class IT_ColorRepository {

	@Autowired
	ColorRepository repo;

	@Test
	void findOneByName_ThenSave_ThenFindOneByName() {
		assertFalse(repo.findOneByName("HotPink").isPresent());

		repo.save(Color.builder().name("HotPink").rgbHexCode("FF69B4").build());

		assertTrue(repo.findOneByName("HotPink").isPresent());
	}

}
