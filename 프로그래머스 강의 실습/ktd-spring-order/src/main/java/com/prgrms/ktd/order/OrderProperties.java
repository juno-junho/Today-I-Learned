package com.prgrms.ktd.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "kdt")
public class OrderProperties implements InitializingBean {

//    @Value("${kdt.version2:v0.0.0}")    // 생성자를 만들지 않아도 version에 이 값이 주입된다.
    private String version;

//    @Value("12")
//    @Value("${kdt.minimum-order-amount}")
    private int minimumOrderAmount;

//    @Value("d, a, b")
//    @Value("${kdt.support-vendors}")
    private List<String> supportVendors;

    private String description;

    @Value("${JAVA_HOME}")
    private String javaHome;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[OrderProperties] version = " + version);
        System.out.println("[OrderProperties] minimumOrderAmount = " + minimumOrderAmount);
        System.out.println("[OrderProperties] supportVendors = " + supportVendors);
        System.out.println("[OrderProperties] javaHome = " + javaHome);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(int minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public List<String> getSupportVendors() {
        return supportVendors;
    }

    public void setSupportVendors(List<String> supportVendors) {
        this.supportVendors = supportVendors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJavaHome() {
        return javaHome;
    }

    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }
}
