package com.kdt.kdtjpa.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.kdt.kdtjpa.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
class ImproveMappingTest {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    void Inheritance_Test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Food food = new Food();
        food.setPrice(2000);
        food.setStockQuantity(200);
        food.setChef("백종원");

        entityManager.persist(food);

        transaction.commit();
    }

    @Test
    void MappedSuperClsss_Test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDateTime(LocalDateTime.now());
        order.setCreatedBy("Junho");
        order.setCreatedAt(LocalDateTime.now());

        entityManager.persist(order);

        transaction.commit();
    }
}