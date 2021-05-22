package com.jimtough.mmm.data.jpa.entity;

import java.util.Objects;

public enum ColorName {
	RED("FF0000"),
	GREEN("00FF00"),
	BLUE("0000FF");

	// This field is redundant since it is also stored in the database.
	// I added it here to make unit tests and test data setup simpler.
	public final String rgbHexcode;

	ColorName(String rgbHexcode) {
		this.rgbHexcode = Objects.requireNonNull(rgbHexcode);
	}
}
