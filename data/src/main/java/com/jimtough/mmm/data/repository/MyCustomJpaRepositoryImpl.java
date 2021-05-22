package com.jimtough.mmm.data.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

// REFERENCE: https://dzone.com/articles/adding-entitymanagerrefresh-to-all-spring-data-rep
public class MyCustomJpaRepositoryImpl<T, ID extends Serializable>
		extends SimpleJpaRepository<T, ID>
		implements MyCustomJpaRepository<T, ID> {

	private final EntityManager entityManager;

	public MyCustomJpaRepositoryImpl(
			final JpaEntityInformation<T, ?> entityInformation,
			final EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void refresh(final T t) {
		entityManager.refresh(t);
	}
}
