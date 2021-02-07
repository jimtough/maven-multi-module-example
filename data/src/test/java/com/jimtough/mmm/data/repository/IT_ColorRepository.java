package com.jimtough.mmm.data.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.jimtough.mmm.data.jpa.entity.Color;
import com.jimtough.mmm.data.jpa.entity.ColorName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
public class IT_ColorRepository {

	@Autowired
	ColorRepository repo;

	@Rollback
	@Test
	void findOneByColorName_ThenSave_ThenFindOneByColorName() {
		assertFalse(repo.findOneByColorName(ColorName.RED).isPresent());

		repo.save(Color.builder().colorName(ColorName.RED).rgbHexCode("FF0000").build());

		assertTrue(repo.findOneByColorName(ColorName.RED).isPresent());
	}

	@Test
	void funWithTheCrudRepositoryInterface() {
		assertEquals(0, repo.count());
		assertFalse(repo.existsById(1L));
		assertTrue(repo.findAll().isEmpty());
		assertFalse(repo.findOneByColorName(ColorName.BLUE).isPresent());
		assertFalse(repo.findOneByColorName(ColorName.GREEN).isPresent());

		repo.save(Color.builder().colorName(ColorName.BLUE).rgbHexCode("00FF00").build());
		Color green = repo.save(Color.builder().colorName(ColorName.GREEN).rgbHexCode("0000FF").build());

		assertEquals(2, repo.count());
		assertTrue(repo.existsById(1L));
		assertTrue(repo.existsById(2L));
		assertEquals(2, repo.findAll().size());
		assertTrue(repo.findOneByColorName(ColorName.BLUE).isPresent());
		assertTrue(repo.findOneByColorName(ColorName.GREEN).isPresent());
		assertFalse(repo.findOneByColorName(ColorName.RED).isPresent());

		repo.delete(green);

		assertEquals(1, repo.count());
		assertEquals(1, repo.findAll().size());
		assertTrue(repo.findOneByColorName(ColorName.BLUE).isPresent());
		assertFalse(repo.findOneByColorName(ColorName.GREEN).isPresent());
		assertFalse(repo.findOneByColorName(ColorName.RED).isPresent());
	}

}
