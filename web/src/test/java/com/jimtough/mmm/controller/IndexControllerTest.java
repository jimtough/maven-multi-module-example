package com.jimtough.mmm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import com.jimtough.mmm.hello.HelloFactory;
import com.jimtough.mmm.world.WorldFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {

	@Mock
	private HelloFactory helloFactoryMock;
	@Mock
	private WorldFactory worldFactoryMock;
	@Mock
	private Model modelMock;

	private IndexController indexController;

	@BeforeEach
	void beforeEach() {
		when(helloFactoryMock.getHello()).thenReturn("HELLO");
		when(worldFactoryMock.getWorld()).thenReturn("WORLD");
		indexController = new IndexController(helloFactoryMock, worldFactoryMock);
	}

	@Test
	void getIndex() {
		String viewName = indexController.getIndex(modelMock);

		assertEquals("index", viewName);
		verify(modelMock).addAttribute(eq("helloString"), eq("HELLO"));
		verify(modelMock).addAttribute(eq("worldString"), eq("WORLD"));
		verify(modelMock).addAttribute(eq("currentTime"), any(LocalDateTime.class));
		verifyNoMoreInteractions(modelMock);
	}

}
