package com.kdt.kdtjpa.domain.order;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Furniture extends Item {
    private int width;
    private int height;
}
