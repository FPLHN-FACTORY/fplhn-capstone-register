package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.CommonHelper;
import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.sinhvien.model.response.SvGiangVienHuongDanResponse;
import com.fpolydatn.core.sinhvien.repository.SvGiangVienHuongDanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PhongTT35
 * @description Tìm ds giảng viên có thể hướng dẫn môn học theo đợt đăng ký hiện tại
 **/
@Service
public final class TimGvCoTheHuongDanUseCase implements IUseCase<String, List<SvGiangVienHuongDanResponse>> {

    private final SvGiangVienHuongDanRepository repository;
    private final CommonHelper commonHelper;

    @Autowired
    public TimGvCoTheHuongDanUseCase(SvGiangVienHuongDanRepository repository,
                                     CommonHelper commonHelper) {
        this.repository = repository;
        this.commonHelper = commonHelper;
    }

    @Override
    public List<SvGiangVienHuongDanResponse> execute(String monDatnId) {
        // Cần bổ sung kiểm tra mã môn tồn tại

        String dotDangKyHienTai = commonHelper.getIdDotDangKyHienTai();
        List<SvGiangVienHuongDanResponse> lst = repository
                .timGvCoTheHuongDanTrongDotTheoMon(monDatnId, dotDangKyHienTai);
        return lst;
    }
}