package com.jimtough.mmm.data.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.jimtough.mmm.data.jpa.entity.Color;
import com.jimtough.mmm.data.jpa.entity.ColorName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class IT_ColorRepository {

	@Autowired
	ColorRepository repo;

	@Test
	void findOneByColorName_ThenSave_ThenFindOneByColorName() {
		assertFalse(repo.findOneByColorName(ColorName.RED).isPresent());

		repo.save(Color.builder().colorName(ColorName.RED).rgbHexCode("FF0000").build());

		assertTrue(repo.findOneByColorName(ColorName.RED).isPresent());
	}

}
