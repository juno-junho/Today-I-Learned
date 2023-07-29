package com.kdt.kdtjpa.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.kdt.kdtjpa.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
public class ProxyTest {

    private final String uuid = UUID.randomUUID().toString();

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Order order = new Order();
        order.setUuid(uuid);
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDateTime(LocalDateTime.now());

        entityManager.persist(order);

        Member member = new Member();
        member.setName("황준호");
        member.setNickName("juno");
        member.setAddress("서울시 광진구");
        member.setAge(26);
        member.setDescription("화이팅!");

        member.addOrder(order);
        entityManager.persist(member);
        transaction.commit();
    }

    @Test
    void proxy() {
        EntityManager entityManager = emf.createEntityManager();

        // 회원 조회 -> 회원의 주문 까지 조회
        Member findMember = entityManager.find(Member.class, 1L);

        log.info("orders is loaded : {}", entityManager.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(findMember.getOrders()));    // 프록시 객체인지 아닌지 확인

        log.info("-------");
        log.info("{}" ,findMember.getOrders().get(0).getMemo());
        log.info("orders is loaded : {}", entityManager.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(findMember.getOrders()));
    }
}
