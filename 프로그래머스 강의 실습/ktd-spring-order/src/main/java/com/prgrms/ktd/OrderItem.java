package com.prgrms.ktd;

import java.util.UUID;

public record OrderItem(UUID productId, long productPrice, long quantity) {
}
