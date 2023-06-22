package com.prgrms.ktd.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProperties implements InitializingBean {

    @Value("${kdt.version2:v0.0.0}")    // 생성자를 만들지 않아도 version에 이 값이 주입된다.
    private String version;

//    @Value("12")
    @Value("${kdt.minimum-order-amount}")
    private int minimumOrderAmount;

//    @Value("d, a, b")
    @Value("${kdt.support-vendors}")
    private List<String> supportVendors;

    @Value("${JAVA_HOME}")
    private String javaHome;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[OrderProperties] version = " + version);
        System.out.println("[OrderProperties] minimumOrderAmount = " + minimumOrderAmount);
        System.out.println("[OrderProperties] supportVendors = " + supportVendors);
        System.out.println("[OrderProperties] javaHome = " + javaHome);
    }
}
