package com.prgrms.ktd;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CircularDepTester {
    /**
     * circular Dependency 테스트
     */
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(CircularConfig.class);
    }
}

@Configuration
class CircularConfig {

    @Bean
    public A a(B b) {
        return new A(b);
    }

    @Bean
    B b(A a) {
        return new B(a);
    }
}

class A {
    private final B b;

    A(B b) {
        this.b = b;
    }
}
class B {
    private final A a;

    B(A a) {
        this.a = a;
    }
}