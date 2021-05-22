package com.jimtough.mmm.data.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import com.jimtough.mmm.data.jpa.entity.Color;
import com.jimtough.mmm.data.jpa.entity.ColorName;
import com.jimtough.mmm.data.jpa.entity.SiteVisitor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@SuppressWarnings("OptionalGetWithoutIsPresent")
@DataJpaTest
public class IT_ColorRepository {

	@Autowired
	ColorRepository colorRepo;
	@Autowired
	SiteVisitorRepository svRepo;

	@Test
	void findOneByColorName_ThenSave_ThenFindOneByColorName() {
		assertFalse(colorRepo.findOneByColorName(ColorName.RED).isPresent());

		colorRepo.save(Color.builder().colorName(ColorName.RED).rgbHexCode(ColorName.RED.rgbHexcode).build());

		assertTrue(colorRepo.findOneByColorName(ColorName.RED).isPresent());
	}

	@Test
	void funWithTheCrudRepositoryInterface() {
		assertEquals(0, colorRepo.count());
		assertFalse(colorRepo.existsById(1L));
		assertTrue(colorRepo.findAll().isEmpty());
		assertFalse(colorRepo.findOneByColorName(ColorName.BLUE).isPresent());
		assertFalse(colorRepo.findOneByColorName(ColorName.GREEN).isPresent());

		colorRepo.save(Color.builder().colorName(ColorName.BLUE).rgbHexCode(ColorName.BLUE.rgbHexcode).build());
		Color green = colorRepo.save(Color.builder().colorName(ColorName.GREEN).rgbHexCode(ColorName.GREEN.rgbHexcode).build());

		assertEquals(2, colorRepo.count());
		assertTrue(colorRepo.existsById(1L));
		assertTrue(colorRepo.existsById(2L));
		assertEquals(2, colorRepo.findAll().size());
		assertTrue(colorRepo.findOneByColorName(ColorName.BLUE).isPresent());
		assertTrue(colorRepo.findOneByColorName(ColorName.GREEN).isPresent());
		assertFalse(colorRepo.findOneByColorName(ColorName.RED).isPresent());

		colorRepo.delete(green);

		assertEquals(1, colorRepo.count());
		assertEquals(1, colorRepo.findAll().size());
		assertTrue(colorRepo.findOneByColorName(ColorName.BLUE).isPresent());
		assertFalse(colorRepo.findOneByColorName(ColorName.GREEN).isPresent());
		assertFalse(colorRepo.findOneByColorName(ColorName.RED).isPresent());
	}

	@Transactional
	@Test
	void addAndRemoveFavoriteColors(TestInfo testInfo) {
		final String testMethodName = testInfo.getTestMethod().get().getName();

		log.info("{} | verify that 'SOMEUSER' does not exist and no {} entities exist", testMethodName, Color.class.getSimpleName());
		assertFalse(svRepo.findOneByUppercaseNickname("SOMEUSER").isPresent());
		assertTrue(colorRepo.findAll().isEmpty());

		log.info("{} | persist new {} entity with name 'SOMEUSER'", testMethodName, SiteVisitor.class.getSimpleName());
		SiteVisitor sv = svRepo.saveAndFlush(SiteVisitor.builder().nickname("SOMEUSER").uppercaseNickname("SOMEUSER").build());
		assertEquals(1, svRepo.findAll().size());

		log.info("{} | persist 2 new {} entities - green and blue", testMethodName, Color.class.getSimpleName());
		Color green = colorRepo.saveAndFlush(Color.builder().colorName(ColorName.GREEN).rgbHexCode(ColorName.GREEN.rgbHexcode).build());
		Color blue = colorRepo.saveAndFlush(Color.builder().colorName(ColorName.BLUE).rgbHexCode(ColorName.BLUE.rgbHexcode).build());
		assertEquals(2, colorRepo.findAll().size());

		log.info("{} | refresh the {} entity with name 'SOMEUSER' and verify it has no favorite colors", testMethodName, SiteVisitor.class.getSimpleName());
		svRepo.refresh(sv);
		assertTrue(sv.getFavoriteColors().isEmpty());

		log.info("{} | add green and blue as favorite colors for {} entity", testMethodName, SiteVisitor.class.getSimpleName());
		sv.getFavoriteColors().add(green);
		sv.getFavoriteColors().add(blue);
		svRepo.saveAndFlush(sv);

		log.info("{} | refresh the {} entity and verify the favorite colors", testMethodName, SiteVisitor.class.getSimpleName());
		svRepo.refresh(sv);
		assertEquals(2, sv.getFavoriteColors().size());

		log.info("{} | refresh the green {} entity and verify it is a favorite of 'SOMEUSER'", testMethodName, Color.class.getSimpleName());
		colorRepo.refresh(green);
		assertEquals(1, green.getSiteVisitorsWhoFavoritedMe().size());

		log.info("{} | refresh the blue {} entity and verify it is a favorite of 'SOMEUSER'", testMethodName, Color.class.getSimpleName());
		colorRepo.refresh(blue);
		assertEquals(1, blue.getSiteVisitorsWhoFavoritedMe().size());

		log.info("{} | retrieve the {} entity and remove blue as a favorite color", testMethodName, SiteVisitor.class.getSimpleName());
		sv.getFavoriteColors().remove(blue);
		svRepo.saveAndFlush(sv);

		log.info("{} | retrieve the blue {} entity and verify it is currently a favorite of nobody", testMethodName, Color.class.getSimpleName());
		colorRepo.refresh(blue);
		assertTrue(blue.getSiteVisitorsWhoFavoritedMe().isEmpty());
	}

}
