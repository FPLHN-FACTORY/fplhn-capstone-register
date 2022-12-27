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
        String maNhomGanNhat = nhomDatnRepository.getNhomTaoGanNhat(dotDangKyHienTai, coSoId);
        Integer count = 0;
        try {
            if (maNhomGanNhat != null) {
                String[] splitStr = maNhomGanNhat.split("_");
                count = Integer.valueOf(splitStr[1]) + 1;
            } else {
                count = 1;
            }
        } catch (Exception e) {
            return null;
        }
        String maNhomMoi = text + "_" + count;
        return maNhomMoi;
    }
}
