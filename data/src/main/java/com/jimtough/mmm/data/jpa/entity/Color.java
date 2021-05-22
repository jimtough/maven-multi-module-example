package com.jimtough.mmm.data.jpa.entity;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Color extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private ColorName colorName;

	private String rgbHexCode;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ColorImage colorImage;

	@Setter(AccessLevel.NONE)
	@Builder.Default
	@ManyToMany(mappedBy = "favoriteColors")
	private Set<SiteVisitor> siteVisitorsWhoFavoritedMe = new HashSet<>();

}
