package com.prgrms.ktd;

import com.prgrms.ktd.order.Order;
import com.prgrms.ktd.order.OrderItem;
import com.prgrms.ktd.order.OrderService;
import com.prgrms.ktd.order.OrderStatus;
import com.prgrms.ktd.voucher.FixedAmountVoucher;
import com.prgrms.ktd.voucher.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


// test context가 만드는 것은 spring extension을 이용해야한다.
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration /*(classes = AppConfiguration.class) */ // 어떤 식으로 application context가 만들어 져야하는지만 알아주고
@SpringJUnitConfig
@ActiveProfiles("local")
public class KdtSpringContextTests {

    @Configuration
    @ComponentScan(
            basePackages = {"com.prgrms.ktd.voucher", "com.prgrms.ktd.order", "com.prgrms.ktd.configuration"}
    )
    static class Config {
       /* @Bean
        VoucherRepository voucherRepository() {
            return new VoucherRepository() {
                @Override
                public Optional<Voucher> findById(UUID voucherId) {
                    return Optional.empty();
                }

                @Override
                public Voucher insert(Voucher voucher) {
                    return null;
                }
            };
        }*/
    }


    @Autowired
    ApplicationContext context;

    @Autowired
    OrderService orderService;

    @Autowired
    VoucherRepository voucherRepository;

    @DisplayName("application context가 생성되어야 한다.")
    @Test
    void testApplicationContext() {
        assertThat(context, notNullValue());
    }

    @DisplayName("VoucherRepository가 bean으로 등록되어 있어야 한다.")
    @Test
    void testVoucherRepositoryCreation() {
        VoucherRepository bean = context.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());
    }

    @DisplayName("VoucherService를 사용해서 주문을 생성할 수 있다.")
    @Test // 통합 테스트
    void testOrderService() {
        // Given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);

        // When
        Order order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

        // Then -> mock으로 전달한것이 아니라 실제 bean으로 등록된 객체간의 상호 협력관계를 통합 테스트 한 것.
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }
}