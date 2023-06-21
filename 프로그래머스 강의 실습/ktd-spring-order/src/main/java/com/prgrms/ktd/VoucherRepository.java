package com.prgrms.ktd;

import java.util.Optional;
import java.util.UUID;

/**
 * JDBC 기술을 이용해서 DB에서 가져올 수도 있고,
 * RDB, NOSQL DB, DOCUMENT DB 등등.. 바뀔수 있기 때문에 INTERFACE로 정의
 * -> 구현체를 별도로 가져가고 관계 설정을 OrderContext에서 다 맺어준다
 */
public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);
}
