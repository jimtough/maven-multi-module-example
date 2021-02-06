package com.jimtough.mmm.data.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Color extends BaseEntity {

	private String name;
	private String rgbHexCode;
	@Lob
	private Byte[] image;

}
