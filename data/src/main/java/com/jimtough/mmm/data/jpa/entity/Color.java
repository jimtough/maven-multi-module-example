package com.jimtough.mmm.data.jpa.entity;

import javax.persistence.*;

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

}
