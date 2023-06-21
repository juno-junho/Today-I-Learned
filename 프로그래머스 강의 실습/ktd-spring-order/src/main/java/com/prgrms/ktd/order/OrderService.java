package com.prgrms.ktd.order;

import com.prgrms.ktd.voucher.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * voucher 서비스와 order에 대한 정보를 기록하고 조회할 수 있는 repository에 대한 의존성을 가진다
 * 객체를 만드는 제어권을 order context에게 넘긴다.
 */
@Service
public class OrderService {
    private final VoucherService voucherService;
    private final OrderRepository orderRepository;

    // 생성 시 서비스에 대한 객체를 외부에서 주입받을 수 있도록.

    public OrderService(VoucherService voucherService, OrderRepository orderRepository) {
        this.voucherService = voucherService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        var voucher = voucherService.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order); // 영속성 보장
        voucherService.useVoucher(voucher);
        return order;
    }

    // voucher가 엉ㅂ서도 만들어 질 수 있도록
    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        return orderRepository.insert(order); // 영속성 보장
    }
}
