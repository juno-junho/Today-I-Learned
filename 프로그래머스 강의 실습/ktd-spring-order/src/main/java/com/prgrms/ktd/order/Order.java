package com.prgrms.ktd.order;

import com.prgrms.ktd.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {

    // 식별자 -> UUID로 많이 만들게 된다.
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
//    private FixedAmountVoucher fixedAmountVoucher;   // 컴파일 타임에 강한 결합도를 가지고 있다. -> 느슨한 결합도로 바꾸고 싶다
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public long totalAmount() {
        long beforeDiscount = orderItems.stream()
                .map(item -> item.productPrice() * item.quantity())
                .reduce(0L, Long::sum);
        // 위임
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Optional<Voucher> getVoucher() {
        return voucher;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
