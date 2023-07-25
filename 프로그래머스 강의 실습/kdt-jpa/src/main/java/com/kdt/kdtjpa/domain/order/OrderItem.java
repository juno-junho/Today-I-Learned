package com.kdt.kdtjpa.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;

    // FK
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "item_id")
    private Long itemId;
}
