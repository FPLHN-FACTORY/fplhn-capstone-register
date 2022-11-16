package com.fpolydatn.util;

import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.repository.NhomDatnRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author hungpv
 */
@Component
public class NhomDatnHelper {
    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private FpolyDatnSession session;

    @Synchronized
    public String genMaNhomTuDong() {
        String coSoId = session.getCoSoId();
        String text = nhomDatnRepository.getMaCoSo(coSoId);
        String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
        Integer count = 0;
        try {
            count = Integer.parseInt(nhomDatnRepository.genMaNhomTuDong(dotDangKyHienTai, coSoId));
        } catch (Exception e) {
            return null;
        }
        count++;
        return text + "_" + count;
    }
}
