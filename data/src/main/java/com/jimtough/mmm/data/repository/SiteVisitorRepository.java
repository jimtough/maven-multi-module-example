package com.jimtough.mmm.data.repository;

import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.SiteVisitor;

public interface SiteVisitorRepository extends MyCustomJpaRepository<SiteVisitor, Long> {

    Optional<SiteVisitor> findOneByUppercaseNickname(String uppercaseNickname);

}
