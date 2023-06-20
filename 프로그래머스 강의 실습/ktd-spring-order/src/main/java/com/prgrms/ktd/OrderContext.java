package com.prgrms.ktd;

import java.util.Optional;
import java.util.UUID;

/**
 * VoucherService, VoucherRepository
 * OrderService, OrderRepository 생성에 대한 책임을 갖게 됨
 * 각각의 repository들과 service간의 wiring, 의존관계 맺는 것을 담당

 * 주문에 대한 전반적인 도메인 객체에 대한 생성 책임을 지고 있다

 * ioc container의 역할
 * -> 개별 객체들의 의존관계 설정 및 객체 생성 및 파괴 관장
 */
public class OrderContext {
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }

    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }
}
