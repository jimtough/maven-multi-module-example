package com.jimtough.mmm.data;

import com.jimtough.mmm.data.repository.MyCustomJpaRepositoryImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This class exists only to keep @DataJpaTest annotation integration tests happy
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = MyCustomJpaRepositoryImpl.class)
public class DummyApplication {}
