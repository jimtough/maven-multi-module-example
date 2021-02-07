package com.jimtough.mmm.data.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import com.jimtough.mmm.data.jpa.entity.Color;
import com.jimtough.mmm.data.jpa.entity.ColorName;
import com.jimtough.mmm.data.jpa.entity.SiteVisitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class IT_SiteVisitorRepository {

	@Autowired
	SiteVisitorRepository svRepo;
	@Autowired
	ColorRepository colorRepo;

	@Test
	void findOneByUppercaseNickname_ThenSave_ThenFindOneByUppercaseNickname() {
		assertFalse(svRepo.findOneByUppercaseNickname("PLOP").isPresent());

		svRepo.save(SiteVisitor.builder().nickname("plop").uppercaseNickname("PLOP").build());

		assertTrue(svRepo.findOneByUppercaseNickname("PLOP").isPresent());
	}

	@Test
	void saveWithOneFavoriteColor() {
		assertFalse(svRepo.findOneByUppercaseNickname("FIZZ").isPresent());
		assertTrue(colorRepo.findAll().isEmpty());

		Color red = colorRepo.save(Color.builder().colorName(ColorName.RED).rgbHexCode("FF0000").build());
		svRepo.save(SiteVisitor.builder().nickname("fizz").uppercaseNickname("FIZZ").favoriteColors(Set.of(red)).build());

		SiteVisitor fizz = svRepo.findOneByUppercaseNickname("FIZZ").get();
		assertEquals(1, fizz.getFavoriteColors().size());
		assertEquals(ColorName.RED, fizz.getFavoriteColors().iterator().next().getColorName());
	}

}
