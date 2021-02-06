package com.jimtough.mmm.data.repository;

import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.SiteVisitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteVisitorRepository extends JpaRepository<SiteVisitor, Long> {

    Optional<SiteVisitor> findOneByUppercaseNickname(String uppercaseNickname);

}
