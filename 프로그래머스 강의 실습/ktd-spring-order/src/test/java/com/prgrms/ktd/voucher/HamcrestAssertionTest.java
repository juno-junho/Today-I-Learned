package com.prgrms.ktd.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class HamcrestAssertionTest {

    @DisplayName("여러 hamcrest matcher 테스트")
    @Test
    void hamcrestTest() {
        assertThat(1 + 1, equalTo(2));
        assertThat(1 + 1, is(2));
        assertThat(1 + 1, anyOf(is(1), is(2)));

        assertThat(1 + 1, not(1));
    }

    @DisplayName("컬렉션에 대한 matcher 테스트")
    @Test
    void hamcrestListMatcherTest() {
        var prices = List.of(1, 2, 3);
        assertThat(prices, hasSize(3));
        assertThat(prices, everyItem(greaterThan(0)));
        assertThat(prices, containsInAnyOrder(3, 4, 2));
        assertThat(prices, hasItem(greaterThanOrEqualTo(2)));
    }
}
