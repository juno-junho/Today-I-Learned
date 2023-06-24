package com.prgrms.ktd;

import com.prgrms.ktd.order.OrderItem;
import com.prgrms.ktd.order.OrderProperties;
import com.prgrms.ktd.order.OrderService;
import com.prgrms.ktd.voucher.FixedAmountVoucher;
import com.prgrms.ktd.voucher.JdbcVoucherRepository;
import com.prgrms.ktd.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    // 구현체를 import 해서 logger를 쓰면 안된다. 꼭 slf4j와 loggerfactory 사용하기
    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) {

        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
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
        logger.info("logger name => {}", logger.getName());
        logger.info("orderProperties.getVersion() = {}", orderProperties.getVersion());
        logger.info("orderProperties.getMinimumOrderAmount() = {}", orderProperties.getMinimumOrderAmount());
        logger.info("orderProperties.getSupportVendors() = {}", orderProperties.getSupportVendors());
        logger.info("orderProperties.getDescription() = {}", orderProperties.getDescription());


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
