package com.jimtough.mmm.data.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.time.ZonedDateTime;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class ColorImage extends BaseEntity {

	// TODO complete this later
	//@Lob
	//private Byte[] image;

	private String description;

	@CreationTimestamp
	private ZonedDateTime createdTimestamp;

	@UpdateTimestamp
	private ZonedDateTime updatedTimestamp;

}
