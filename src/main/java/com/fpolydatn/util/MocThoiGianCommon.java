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

    public boolean checkMocThoiGianSinhVien() {
        boolean check = false;
        String dotDangKyHienTaiId = commonHelper.getDotDangKyHienTai();
        KhoangThoiGianResponse khoangThoiGianResponse = dotDangKyRepository.findMocThoiGianSinhVien(dotDangKyHienTaiId);
        Long currentTime = convertDateToTimeStampSecond();
        if (currentTime >= khoangThoiGianResponse.getNgayBatDau() && currentTime <= khoangThoiGianResponse.getNgayKetThuc()) {
            check = true;
        }
        return check;
    }

    public boolean checkMocThoiGianGiangVien() {
        boolean check = false;
        String dotDangKyHienTaiId = commonHelper.getDotDangKyHienTai();
        KhoangThoiGianResponse khoangThoiGianResponse = dotDangKyRepository.findMocThoiGianGiangVien(dotDangKyHienTaiId);
        Long currentTime = convertDateToTimeStampSecond();
        if (currentTime >= khoangThoiGianResponse.getNgayBatDau() && currentTime <= khoangThoiGianResponse.getNgayKetThuc()) {
            check = true;
        }
        return check;
    }

    public boolean checkMocThoiGianChuNhiem() {
        boolean check = false;
        String dotDangKyHienTaiId = commonHelper.getDotDangKyHienTai();
        KhoangThoiGianResponse khoangThoiGianResponse = dotDangKyRepository.findMocThoiGianChuNhiem(dotDangKyHienTaiId);
        Long currentTime = convertDateToTimeStampSecond();
        if (currentTime >= khoangThoiGianResponse.getNgayBatDau() && currentTime <= khoangThoiGianResponse.getNgayKetThuc()) {
            check = true;
        }
        return check;
    }
}
