package com.jimtough.mmm.data.repository;

import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.LanguageSpecificHello;

public interface LanguageSpecificHelloRepository extends MyCustomJpaRepository<LanguageSpecificHello, Long> {

    Optional<LanguageSpecificHello> findOneByKey(String key);

}
