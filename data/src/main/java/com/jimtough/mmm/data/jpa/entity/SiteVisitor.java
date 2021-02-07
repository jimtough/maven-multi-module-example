package com.jimtough.mmm.data.jpa.entity;

import javax.persistence.*;

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

	@ManyToMany
	@JoinTable(name = "site_visitor_favorite_colors",
		joinColumns = @JoinColumn(name = "color_id"),
		inverseJoinColumns = @JoinColumn(name = "site_visitor_id"))
	private Set<Color> favoriteColors;

}
