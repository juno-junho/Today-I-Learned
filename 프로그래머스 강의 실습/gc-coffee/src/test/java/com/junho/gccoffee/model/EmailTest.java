package com.junho.gccoffee.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    void testEmail() {
        assertThatThrownBy(() -> new Email("acccc"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testValidEmail() {
        Email email = new Email("hello@gmail.com");
        assertThat(email.getAddress()).isEqualTo("hello@gmail.com");
    }

    @Test
    void testEqualEmail() {
        Email email = new Email("hello@gmail.com");
        Email email2 = new Email("hello@gmail.com");
        assertThat(email).isEqualTo(email2);
    }
}