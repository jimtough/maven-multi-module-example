package com.jimtough.mmm;

import com.jimtough.mmm.data.repository.MyCustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = MyCustomJpaRepositoryImpl.class)
public class MmmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmmApplication.class, args);
	}

}
