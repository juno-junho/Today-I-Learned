package com.prgrms.ktd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

/**
 * VoucherService, VoucherRepository
 * OrderService, OrderRepository 생성에 대한 책임을 갖게 됨
 * 각각의 repository들과 service간의 wiring, 의존관계 맺는 것을 담당
 * <p>
 * 주문에 대한 전반적인 도메인 객체에 대한 생성 책임을 지고 있다
 * <p>
 * ioc container의 역할
 * -> 개별 객체들의 의존관계 설정 및 객체 생성 및 파괴 관장
 */
@Configuration  // configuration metadata라고 알려주는 것. bean 정의한 도면이다!
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return voucherId -> Optional.empty();
    }

    @Bean
    public OrderRepository orderRepository() {
        return order -> {

        };
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }
}
