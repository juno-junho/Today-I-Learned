package com.prgrms.ktd.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * voucher에 대한 정보를 불러와서 voucher 클래스를 생성해준다
 */
@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

//    public VoucherService(VoucherRepository voucherRepository) {
//        this.voucherRepository = voucherRepository;
//    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("cannot find a voucher for " + voucherId));
    }
    public void useVoucher(Voucher voucher) {

    }
}
