package com.jimtough.mmm;

import static org.junit.jupiter.api.Assertions.*;

import com.jimtough.mmm.mvc.controller.IndexController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class IT_MmmApplication {

	@Autowired
	ApplicationContext ctx;

	@Test
	void contextLoads() {
		assertNotNull(ctx.getBean(IndexController.class));
	}

}
