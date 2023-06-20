package com.prgrms.ktd;

import java.util.UUID;

/**
 * voucher에 대한 정보를 불러와서 voucher 클래스를 생성해준다
 */
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("cannot find a voucher for " + voucherId));
    }
    public void useVoucher(Voucher voucher) {

    }
}
