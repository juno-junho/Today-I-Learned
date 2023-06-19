package com.prgrms.ktd;

import java.util.List;
import java.util.UUID;

public class Order {

    // 식별자 -> UUID로 많이 만들게 된다.
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
//    private FixedAmountVoucher fixedAmountVoucher;   // 컴파일 타임에 강한 결합도를 가지고 있다. -> 느슨한 결합도로 바꾸고 싶다
    private Voucher voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = voucher;
    }

    public long totalAmount() {
        Long beforeDiscount = orderItems.stream()
                .map(item -> item.productPrice() * item.quantity())
                .reduce(0L, Long::sum);
        return voucher.discount(beforeDiscount); // 위임
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
