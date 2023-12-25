package com.fpolydatn.core.common;

import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author hungpv
 */
@Service
@Scope(value = "singleton")
public final class NhomDatnHelper {

    private final NhomDatnRepository nhomDatnRepository;
    private final CommonHelper commonHelper;
    private final FpolyDatnSession session;

    public NhomDatnHelper(CommonHelper commonHelper,
                          FpolyDatnSession session,
                          @Qualifier(NhomDatnRepository.NAME)
                                  NhomDatnRepository nhomDatnRepository) {
        this.commonHelper = commonHelper;
        this.nhomDatnRepository = nhomDatnRepository;
        this.session = session;
    }

    public synchronized String genMaNhomTuDong() {
        String coSoId = session.getCoSoId();
        String text = nhomDatnRepository.getMaCoSo(coSoId);
        String dotDangKyHienTai = commonHelper.getIdDotDangKyHienTai();
        String maNhomGanNhat = nhomDatnRepository.getNhomTaoGanNhat(dotDangKyHienTai, coSoId);
        Integer count;
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
