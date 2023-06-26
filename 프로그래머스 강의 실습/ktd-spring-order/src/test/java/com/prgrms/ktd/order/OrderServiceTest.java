package com.prgrms.ktd.order;

import com.prgrms.ktd.voucher.FixedAmountVoucher;
import com.prgrms.ktd.voucher.MemoryVoucherRepository;
import com.prgrms.ktd.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;


class OrderServiceTest {

    static class OrderRepositoryStub implements OrderRepository {
        @Override
        public Order insert(Order order) {
            return null;
        }
    }

    @Test
    @DisplayName("오더가 생성되어야 한다. (stub)")
    void createOrder() {
        // Given
        var voucherRepository = new MemoryVoucherRepository();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);
        OrderService sut = new OrderService(new VoucherService(voucherRepository), new OrderRepositoryStub());

        // When
        Order order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

        // Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }

    @Test
    @DisplayName("오더가 생성되어야 한다. (mock)") // 행위 관점에서 생각해야한다.
    void createOrderByMock() {
        // Given
        var voucherServiceMock = mock(VoucherService.class);
        var orderRepositoryMock = mock(OrderRepository.class);
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        when(voucherServiceMock.getVoucher(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);
        var sut = new OrderService(voucherServiceMock, orderRepositoryMock);

        // When
        Order order = sut.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

        // Then
        assertThat(order.totalAmount(), is(100L));  // 상태 검증
        assertThat(order.getVoucher().isEmpty(), is(false));    // 상태 검증

        InOrder inOrder = inOrder(voucherServiceMock, orderRepositoryMock); // 순서 검증할 수 있음. -> 각각의 mock들이 어떤 순서대로 호출하는지 판별 가능
        inOrder.verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());   // 행위 검증
        inOrder.verify(orderRepositoryMock).insert(order);// 행위 검증
        inOrder.verify(voucherServiceMock).useVoucher(fixedAmountVoucher);// 행위 검증
    }
}