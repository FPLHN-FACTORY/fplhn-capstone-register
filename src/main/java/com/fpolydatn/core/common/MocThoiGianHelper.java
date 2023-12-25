package com.fpolydatn.core.common;

import com.fpolydatn.core.daotao.model.response.KhoangThoiGianResponse;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.fpolydatn.util.DateTimeUtil.convertDateToTimeStampSecond;

/**
 * @author thangncph26123
 */
@Component
public class MocThoiGianHelper {

    private final CommonHelper commonHelper;
    private final DotDangKyRepository dotDangKyRepository;

    @Autowired
    public MocThoiGianHelper(CommonHelper commonHelper,
                             @Qualifier(DotDangKyRepository.NAME) DotDangKyRepository dotDangKyRepository) {
        this.commonHelper = commonHelper;
        this.dotDangKyRepository = dotDangKyRepository;
    }

    public boolean checkMocThoiGianSinhVien() {
        return checkMocThoiGian("SinhVien");
    }

    public boolean checkMocThoiGianGiangVien() {
        return checkMocThoiGian("GiangVien");
    }

    public boolean checkMocThoiGianChuNhiem() {
        return checkMocThoiGian("ChuNhiem");
    }
    private boolean checkMocThoiGian(String type) {
        boolean check = false;
        String dotDangKyHienTaiId = commonHelper.getIdDotDangKyHienTai();
        if (dotDangKyHienTaiId != null) {
            KhoangThoiGianResponse khoangThoiGianResponse = null;
            switch (type) {
                case "SinhVien":
                    khoangThoiGianResponse = dotDangKyRepository.findMocThoiGianSinhVien(dotDangKyHienTaiId);
                    break;
                case "GiangVien":
                    khoangThoiGianResponse = dotDangKyRepository.findMocThoiGianGiangVien(dotDangKyHienTaiId);
                    break;
                case "ChuNhiem":
                    khoangThoiGianResponse = dotDangKyRepository.findMocThoiGianChuNhiem(dotDangKyHienTaiId);
                    break;
                default:
                    // Handle unknown type or throw an exception
                    break;
            }

            if (khoangThoiGianResponse != null) {
                Long currentTime = convertDateToTimeStampSecond();
                if (currentTime >= khoangThoiGianResponse.getNgayBatDau()
                        && currentTime <= khoangThoiGianResponse.getNgayKetThuc()) {
                    check = true;
                }
            }
        }
        return check;
    }
}
