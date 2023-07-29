package com.kdt.kdtjpa.domain.parent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@IdClass(ParentId.class)
public class Parent {
    @Id
    private String id1;
    @Id
    private String id2;
}

