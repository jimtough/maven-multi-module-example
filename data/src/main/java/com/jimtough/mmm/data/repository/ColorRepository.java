package com.jimtough.mmm.data.repository;

import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.Color;
import com.jimtough.mmm.data.jpa.entity.ColorName;

public interface ColorRepository extends MyCustomJpaRepository<Color, Long> {

	Optional<Color> findOneByColorName(ColorName colorName);

}
