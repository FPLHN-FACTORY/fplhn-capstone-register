package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.sinhvien.model.request.RoiNhomDatnRequest;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.NhomDatnRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Optional;

/**
 * @author PhongTT35
 * @description N/A
 **/
public final class RoiNhomUseCase implements IUseCase<RoiNhomDatnRequest, SinhVien> {

    private final SinhVienRepository sinhVienRepository;
    private final NhomDatnRepository nhomDatnRepository;

    @Autowired
    public RoiNhomUseCase(SinhVienRepository sinhVienRepository,
                          NhomDatnRepository nhomDatnRepository) {
        this.sinhVienRepository = sinhVienRepository;
        this.nhomDatnRepository = nhomDatnRepository;
    }

    @Override
    public SinhVien execute(RoiNhomDatnRequest input) {
        String sinhVienId = input.getSinhVienId();
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        if (sinhVien == null) {
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        String nhomDatnId = sinhVien.getNhomId();
        String truongNhomId;
        if (nhomDatnId == null || "".equals(nhomDatnId)) {
            throw new RestApiException(Message.SINH_VIEN_KHONG_CO_NHOM);
        } else {
            NhomDatn nhomDatn = nhomDatnRepository.findById(nhomDatnId).orElse(null);
            if (nhomDatn == null) {
                throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
            }
            truongNhomId = nhomDatn.getTruongNhomId();
        }

        if (sinhVienId.equals(truongNhomId)) {
            Integer soThanhVien = nhomDatnRepository.countSoLuongThanhVien(nhomDatnId);
            if (soThanhVien > 1) {
                throw new RestApiException(Message.ROI_NHOM_KHI_LA_TRUONG_NHOM);
            } else {
                nhomDatnRepository.deleteById(nhomDatnId);
            }
        }

        sinhVien.setNhomId(null);
        sinhVien.setThoiDiemRoiNhomGanNhat(Calendar.getInstance().getTimeInMillis());
        sinhVien.setMonDatnId(null);
        return sinhVienRepository.save(sinhVien);
    }
}
