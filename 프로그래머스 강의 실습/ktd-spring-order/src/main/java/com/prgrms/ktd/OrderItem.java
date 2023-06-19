package com.prgrms.ktd;

import java.util.UUID;

// record => 알아서 불변 객체로 만들어 준다.
public record OrderItem(UUID productId, long productPrice, long quantity) {
}
