package com.kdt.kdtjpa.domain.order;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Food extends Item {
    private String chef;
}
