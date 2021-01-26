package com.jimtough.mmm.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.LanguageSpecificHello;
import com.jimtough.mmm.data.repository.LanguageSpecificHelloRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HelloFactoryImplTest {

	@Mock
	private LanguageSpecificHelloRepository languageSpecificHelloRepositoryMock;
	@Mock
	private LanguageSpecificHello languageSpecificHelloMock;

	private HelloFactoryImpl helloFactory;

	@BeforeEach
	void beforeEach() {
		helloFactory = new HelloFactoryImpl(languageSpecificHelloRepositoryMock);
	}

	@Test
	void getHello_nominal() {
		when(languageSpecificHelloMock.getValue()).thenReturn("THE-HELLO-STRING");
		when(languageSpecificHelloRepositoryMock.findOneByKey(anyString()))
				.thenReturn(Optional.of(languageSpecificHelloMock));

		assertEquals("THE-HELLO-STRING", helloFactory.getHello());
	}

	@Test
	void getHelloWhenLanguageKeyDoesNotExistInDatabase() {
		when(languageSpecificHelloRepositoryMock.findOneByKey(anyString()))
				.thenReturn(Optional.empty());

		assertEquals(HelloFactoryImpl.DEFAULT_ENGLISH_HELLO, helloFactory.getHello());
	}

}
