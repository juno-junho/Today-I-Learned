package com.prgrms.ktd;

import com.prgrms.ktd.configuration.YamlPropertiesFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
//@ComponentScan(basePackages = {"com.prgrms.ktd.order", "com.prgrms.ktd.voucher"}) // error prone
//@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
@ComponentScan(basePackages = {"com.prgrms.ktd.voucher", "com.prgrms.ktd.order", "com.prgrms.ktd.configuration"})
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MemoryVoucherRepository.class)})

@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

    @Bean(initMethod = "init")
    public BeanOne beanOne() {
        return new BeanOne();
    }
}

class BeanOne implements InitializingBean {

    public void init() {
        System.out.println("[BeanOne] init called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[BeanOne] afterPropertiesSet called!");
    }
}
