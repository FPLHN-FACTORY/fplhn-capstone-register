package com.fpolydatn.util;

import com.fpolydatn.core.daotao.model.response.KhoangThoiGianResponse;
import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.fpolydatn.util.DateTimeUtil.convertDateToTimeStampSecond;

/**
 * @author thangncph26123
 */
@Component
public class MocThoiGianCommon {

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    public KhoangThoiGianResponse findMocThoiGianSinhVien() {
        String dotDangKyHienTaiId = commonHelper.getDotDangKyHienTai();
        return dotDangKyRepository.findMocThoiGianSinhVien(dotDangKyHienTaiId);
    }

    public KhoangThoiGianResponse findMocThoiGianGiangVien() {
        String dotDangKyHienTaiId = commonHelper.getDotDangKyHienTai();
        return dotDangKyRepository.findMocThoiGianGiangVien(dotDangKyHienTaiId);
    }

    public KhoangThoiGianResponse findMocThoiGianChuNhiem() {
        String dotDangKyHienTaiId = commonHelper.getDotDangKyHienTai();
        return dotDangKyRepository.findMocThoiGianChuNhiem(dotDangKyHienTaiId);
    }
}
