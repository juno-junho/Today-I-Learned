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
class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert() {
        Member member = new Member();
        member.setName("junhoHwang");
        member.setAddress("서울특별시 광진구");
        member.setAge(26);
        member.setNickName("juno");
        member.setDescription("백엔드 개발자에요");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void 잘못된_설계() {
        Member member = new Member();
        member.setName("kanghonggu");
        member.setAddress("서울시 동작구(만) 움직이면 쏜다.");
        member.setAge(33);
        member.setNickName("guppy.kang");
        member.setDescription("백앤드 개발자에요.");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);  // member 저장
        Member memberEntity = entityManager.find(Member.class, 1L);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 전화주세요.");
        order.setMemberId(memberEntity.getId()); // 외래키를 직접 지정

        entityManager.persist(order);   // order 저장 (영속화)
        transaction.commit();   // insert 쿼리 날라감

        Order orderEntity = entityManager.find(Order.class, order.getUuid());
        // FK 를 이용해 회원 다시 조회
        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
        // orderEntity.getMember() // 객체중심 설계라면 객체그래프 탐색을 해야하지 않을까?
        log.info("nick : {}", orderMemberEntity.getNickName());
    }

}