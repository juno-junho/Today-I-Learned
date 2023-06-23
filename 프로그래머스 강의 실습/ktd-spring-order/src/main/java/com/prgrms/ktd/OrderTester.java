package com.prgrms.ktd;

import com.prgrms.ktd.order.OrderItem;
import com.prgrms.ktd.order.OrderProperties;
import com.prgrms.ktd.order.OrderService;
import com.prgrms.ktd.voucher.FixedAmountVoucher;
import com.prgrms.ktd.voucher.JdbcVoucherRepository;
import com.prgrms.ktd.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
                /**
         * environment 가져오기
         */
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("dev");
        applicationContext.refresh();
//        String version = environment.getProperty("kdt.version");
//        Integer minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
//        List supportVendors = environment.getProperty("kdt.support-vendors", List.class);
//        List description = environment.getProperty("kdt.description", List.class);
//
//        System.out.println("version = " + version);
//        System.out.println("minimumOrderAmount = " + minimumOrderAmount);
//        System.out.println("property = " + supportVendors);
//        System.out.println("description = " + description);
        OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);
        System.out.println("orderProperties.getVersion() = " + orderProperties.getVersion());
        System.out.println("orderProperties.getMinimumOrderAmount() = " + orderProperties.getMinimumOrderAmount());
        System.out.println("orderProperties.getSupportVendors() = " + orderProperties.getSupportVendors());
        System.out.println("orderProperties.getDescription() = " + orderProperties.getDescription());


        var customerId = UUID.randomUUID();
//        var orderService = orderContext.orderService();
//        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        // 아래와 같이 직접 applicationContext로 부터 가지고 오는 케이스 없기 때문에 걱정하지 않아도 된다.
//        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
//        var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository instanceof JdbcVoucherRepository));
        System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository.getClass().getCanonicalName()));

        /**
         * singleton 확인
         */
//        System.out.println("voucherRepository2 = " + voucherRepository2);
//        System.out.println("voucherRepository = " + voucherRepository);
//        System.out.println(MessageFormat.format("{0}", voucherRepository == voucherRepository2));
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        var orderService = applicationContext.getBean(OrderService.class);
        var orderItems = new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1L));
        }};
        var order = orderService.createOrder(customerId, orderItems, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

        applicationContext.close();
    }
}
