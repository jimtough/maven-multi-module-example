package com.jimtough.mmm.data.repository;

import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.LanguageSpecificHello;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageSpecificHelloRepository extends JpaRepository<LanguageSpecificHello, Long> {

    Optional<LanguageSpecificHello> findOneByKey(String key);

}
