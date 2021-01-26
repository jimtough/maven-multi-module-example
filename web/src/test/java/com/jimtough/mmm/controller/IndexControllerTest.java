package com.jimtough.mmm.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import com.jimtough.mmm.hello.HelloFactory;
import com.jimtough.mmm.world.WorldFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

		ArgumentCaptor<LocalDateTime> ldtArgumentCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
		assertEquals("index", viewName);
		verify(modelMock).addAttribute(eq("helloString"), eq("HELLO"));
		verify(modelMock).addAttribute(eq("worldString"), eq("WORLD"));
		verify(modelMock).addAttribute(eq("currentTime"), ldtArgumentCaptor.capture());
		assertFalse(ldtArgumentCaptor.getValue().isAfter(LocalDateTime.now()));
		verifyNoMoreInteractions(modelMock);
	}

	@Test
	void getIndexWithMockMVC() throws Exception {
		// standaloneSetup creates a MockMvc that is very lightweight, without a Spring application context
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

		mockMvc.perform(get("/"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("index"));
	}

}
