package com.jimtough.mmm.mvc.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.SiteVisit;
import com.jimtough.mmm.data.jpa.entity.SiteVisitor;
import com.jimtough.mmm.data.repository.SiteVisitRepository;
import com.jimtough.mmm.data.repository.SiteVisitorRepository;
import com.jimtough.mmm.hello.HelloFactory;
import com.jimtough.mmm.mvc.command.SubmitVisitorNameCommand;
import com.jimtough.mmm.mvc.session.SiteVisitorSessionStuff;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {

	private static final String FAKE_NICKNAME = "foobar";

	@Mock
	private HelloFactory helloFactoryMock;
	@Mock
	private WorldFactory worldFactoryMock;
	@Mock
	private SiteVisitorRepository siteVisitorRepositoryMock;
	@Mock
	private SiteVisitRepository siteVisitRepositoryMock;

	@Mock
	private Model modelMock;
	@Mock
	SiteVisitorSessionStuff siteVisitorSessionStuffMock;
	@Mock
	SiteVisitor siteVisitorMock;
	@Mock
	SiteVisit siteVisitMock;
	@Mock
	SubmitVisitorNameCommand submitVisitorNameCommandMock;
	@Mock
	BindingResult bindingResultMock;
	@Mock
	FieldError fieldErrorMock;

	private IndexController indexController;

	@BeforeEach
	void beforeEach() {
		indexController = new IndexController(helloFactoryMock, worldFactoryMock, siteVisitorRepositoryMock, siteVisitRepositoryMock);
	}

	//---------------------------------------------------------------------------------------------

	@Test
	void redirectToIndexView() {
		String viewName = indexController.redirectToIndexView();

		assertEquals("redirect:/", viewName);
	}

	//---------------------------------------------------------------------------------------------

	@Test
	void getIndexView() {
		when(helloFactoryMock.getHello()).thenReturn("HELLO");
		when(worldFactoryMock.getWorld()).thenReturn("WORLD");
		when(siteVisitorSessionStuffMock.getNickname()).thenReturn(Optional.empty());

		String viewName = indexController.getIndexView(modelMock, siteVisitorSessionStuffMock);

		ArgumentCaptor<LocalDateTime> ldtArgumentCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
		assertEquals("index", viewName);
		verify(modelMock).addAttribute(eq("helloString"), eq("HELLO"));
		verify(modelMock).addAttribute(eq("worldString"), eq("WORLD"));
		verify(modelMock).addAttribute(eq("submitVisitorNameCommand"), notNull());
		verify(modelMock).addAttribute(eq("currentTime"), ldtArgumentCaptor.capture());
		assertFalse(ldtArgumentCaptor.getValue().isAfter(LocalDateTime.now()));
		verifyNoMoreInteractions(modelMock);
	}

	@Test
	void getIndexViewWhenNicknameIsSetInSessionObject() {
		when(helloFactoryMock.getHello()).thenReturn("HELLO");
		when(siteVisitorSessionStuffMock.getNickname()).thenReturn(Optional.of(FAKE_NICKNAME));

		String viewName = indexController.getIndexView(modelMock, siteVisitorSessionStuffMock);

		assertEquals("index", viewName);
		verify(modelMock).addAttribute(eq("helloString"), eq("HELLO"));
		verify(modelMock).addAttribute(eq("worldString"), eq(FAKE_NICKNAME));
		verify(modelMock, never()).addAttribute(eq("submitVisitorNameCommand"), any());
		verify(modelMock, never()).addAttribute(eq("currentTime"), any());
		verifyNoMoreInteractions(modelMock);
		verifyNoInteractions(worldFactoryMock);
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

	//---------------------------------------------------------------------------------------------

	@Test
	void handleSubmitVisitorName() {
		when(siteVisitorRepositoryMock.findOneByUppercaseNickname(eq(FAKE_NICKNAME.toUpperCase())))
				.thenReturn(Optional.empty());
		when(siteVisitorRepositoryMock.save(notNull())).thenReturn(siteVisitorMock);
		when(siteVisitRepositoryMock.save(notNull())).thenReturn(siteVisitMock);

		indexController.handleSubmitVisitorName(siteVisitorSessionStuffMock, FAKE_NICKNAME);

		verify(siteVisitorSessionStuffMock).setNickname(eq(FAKE_NICKNAME));
		verify(siteVisitorRepositoryMock).save(notNull());
		verify(siteVisitRepositoryMock).save(notNull());
	}

	@Test
	void handleSubmitVisitorNameWhenMatchingEntityExists() {
		when(siteVisitorRepositoryMock.findOneByUppercaseNickname(eq(FAKE_NICKNAME.toUpperCase())))
				.thenReturn(Optional.of(siteVisitorMock));
		when(siteVisitRepositoryMock.save(notNull())).thenReturn(siteVisitMock);

		indexController.handleSubmitVisitorName(siteVisitorSessionStuffMock, FAKE_NICKNAME);

		verify(siteVisitorSessionStuffMock).setNickname(eq(FAKE_NICKNAME));
		verify(siteVisitorRepositoryMock, never()).save(any());
		verify(siteVisitRepositoryMock).save(notNull());
	}

	//---------------------------------------------------------------------------------------------

	@Test
	void submitVisitorName() {
		when(submitVisitorNameCommandMock.getNickname()).thenReturn(FAKE_NICKNAME);
		when(siteVisitorRepositoryMock.findOneByUppercaseNickname(eq(FAKE_NICKNAME.toUpperCase())))
				.thenReturn(Optional.empty());
		when(siteVisitorRepositoryMock.save(notNull())).thenReturn(siteVisitorMock);
		when(siteVisitRepositoryMock.save(notNull())).thenReturn(siteVisitMock);

		String viewName = indexController.submitVisitorName(
				modelMock, siteVisitorSessionStuffMock, submitVisitorNameCommandMock, bindingResultMock);

		assertEquals("redirect:/", viewName);
		verify(siteVisitorSessionStuffMock).setNickname(eq(FAKE_NICKNAME));
		verify(siteVisitorRepositoryMock).save(notNull());
		verify(siteVisitRepositoryMock).save(notNull());
	}

	@Test
	void submitVisitorNameWhenBindingResultContainsErrors() {
		when(bindingResultMock.hasErrors()).thenReturn(true);
		when(fieldErrorMock.getField()).thenReturn("foo");
		when(fieldErrorMock.getDefaultMessage()).thenReturn("bar");
		when(bindingResultMock.getFieldErrors()).thenReturn(List.of(fieldErrorMock));
		when(helloFactoryMock.getHello()).thenReturn("HELLO");
		when(worldFactoryMock.getWorld()).thenReturn("WORLD");

		String viewName = indexController.submitVisitorName(
				modelMock, siteVisitorSessionStuffMock, submitVisitorNameCommandMock, bindingResultMock);

		assertEquals("index", viewName);
		verify(modelMock).addAttribute(eq("helloString"), eq("HELLO"));
		verify(modelMock).addAttribute(eq("worldString"), eq("WORLD"));
		verifyNoInteractions(siteVisitorRepositoryMock, siteVisitRepositoryMock, siteVisitorSessionStuffMock);
	}

}
