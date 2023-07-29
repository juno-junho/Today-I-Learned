package com.kdt.kdtjpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.kdt.kdtjpa.domain.order.OrderStatus.OPENED;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void test() {
        String uuid = UUID.randomUUID().toString();

        Order order = new Order();
        order.setUuid(uuid);
        order.setOrderStatus(OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setMemo("부재시 전화주세요.");
        order.setCreatedBy("Junho");
        order.setCreatedAt(LocalDateTime.now());
        order.setMemberId(1L);

        orderRepository.save(order);

        Order order1 = orderRepository.findById(uuid).get();
        List<Order> all = orderRepository.findAll();
    }
}