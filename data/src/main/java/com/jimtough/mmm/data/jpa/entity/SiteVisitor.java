package com.jimtough.mmm.data.jpa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.util.Set;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class SiteVisitor extends BaseEntity {

	private String nickname;
	private String uppercaseNickname;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "siteVisitor")
	private Set<SiteVisit> siteVisits;

}
