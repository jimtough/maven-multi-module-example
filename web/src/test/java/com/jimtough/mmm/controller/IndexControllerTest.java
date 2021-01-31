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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
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
		indexController = new IndexController(helloFactoryMock, worldFactoryMock);
	}

	@Test
	void getIndexView() {
		when(helloFactoryMock.getHello()).thenReturn("HELLO");
		when(worldFactoryMock.getWorld()).thenReturn("WORLD");

		String viewName = indexController.getIndexView(modelMock);

		ArgumentCaptor<LocalDateTime> ldtArgumentCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
		assertEquals("index", viewName);
		verify(modelMock).addAttribute(eq("helloString"), eq("HELLO"));
		verify(modelMock).addAttribute(eq("worldString"), eq("WORLD"));
		verify(modelMock).addAttribute(eq("currentTime"), ldtArgumentCaptor.capture());
		assertFalse(ldtArgumentCaptor.getValue().isAfter(LocalDateTime.now()));
		verifyNoMoreInteractions(modelMock);
	}

	@Test
	void redirectToIndexView() {
		String viewName = indexController.redirectToIndexView();

		assertEquals("redirect:/", viewName);
	}

	@Test
	void getIndexViewWithMockMVC() throws Exception {
		// standaloneSetup creates a MockMvc that is very lightweight, without a Spring application context
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

		mockMvc.perform(get("/"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("index"));
	}

	// Interesting... This MockMVC test yields results that are inconsistent with how the application functions.
	// If you try to use something like "/index.html" or "/index.htm" in the browser, you get a 404 error.
	@ParameterizedTest
	@ValueSource(strings = {"/index", "/index.html", "/index.htm", "/index.anything.you.like.can.go.here"})
	void redirectToIndexViewWithMockMVC(String url) throws Exception {
		// standaloneSetup creates a MockMvc that is very lightweight, without a Spring application context
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

		mockMvc.perform(get(url))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(view().name("redirect:/"));
	}

	@ParameterizedTest
	@ValueSource(strings = {"/indexxx", "/no-mapping-for-this-guy"})
	void invalidUrlsWithMockMVC(String url) throws Exception {
		// standaloneSetup creates a MockMvc that is very lightweight, without a Spring application context
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

		mockMvc.perform(get(url)).andExpect(status().isNotFound());
	}

}
