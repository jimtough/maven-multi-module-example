package com.jimtough.mmm.data.jpa.entity;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class LanguageSpecificHello extends BaseEntity {

    private String key;
    private String value;

}
