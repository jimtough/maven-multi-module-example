package com.jimtough.mmm.data.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import java.time.ZonedDateTime;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class SiteVisit extends BaseEntity {

	@CreationTimestamp
	private ZonedDateTime visitTimestamp;

	@ManyToOne
	private SiteVisitor siteVisitor;

}
