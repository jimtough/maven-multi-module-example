package com.jimtough.mmm.data.repository;

import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.Color;
import com.jimtough.mmm.data.jpa.entity.ColorName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {

    Optional<Color> findOneByColorName(ColorName colorName);

}
