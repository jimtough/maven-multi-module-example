package com.jimtough.mmm.data.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.jimtough.mmm.data.jpa.entity.SiteVisit;
import com.jimtough.mmm.data.jpa.entity.SiteVisitor;

public interface SiteVisitRepository extends MyCustomJpaRepository<SiteVisit, Long> {

	// REFERENCE: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details
	// REFERENCE: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords
	Set<SiteVisit> findByVisitTimestampAfter(ZonedDateTime zdt);
	Set<SiteVisit> findBySiteVisitor(SiteVisitor siteVisitor);
	Optional<SiteVisit> findFirstBySiteVisitorOrderByVisitTimestampDesc(SiteVisitor siteVisitor);
	List<SiteVisit> findFirst10BySiteVisitorOrderByVisitTimestampDesc(SiteVisitor siteVisitor);

}
