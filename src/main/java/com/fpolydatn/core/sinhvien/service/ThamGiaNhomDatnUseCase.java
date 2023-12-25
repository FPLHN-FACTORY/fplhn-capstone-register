package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.sinhvien.model.request.ThamGiaNhomDatnAuthRequest;
import com.fpolydatn.core.sinhvien.model.request.ThamGiaNhomDatnRequest;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.NhomDatnRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Service
public final class ThamGiaNhomDatnUseCase implements IUseCase<ThamGiaNhomDatnRequest, SinhVien> {

    private final SinhVienRepository sinhVienRepository;
    private final NhomDatnRepository nhomDatnRepository;

    @Autowired
    public ThamGiaNhomDatnUseCase(
            @Qualifier(SinhVienRepository.NAME) SinhVienRepository sinhVienRepository,
            @Qualifier(NhomDatnRepository.NAME) NhomDatnRepository nhomDatnRepository) {
        this.sinhVienRepository = sinhVienRepository;
        this.nhomDatnRepository = nhomDatnRepository;
    }

    @Override
    public SinhVien execute(ThamGiaNhomDatnRequest request) {
        if (request instanceof ThamGiaNhomDatnAuthRequest) {
            ThamGiaNhomDatnAuthRequest authRequest = (ThamGiaNhomDatnAuthRequest) request;
            validate(authRequest);
        }

        String sinhVienId = request.getSinhVienId();
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        if (sinhVien == null) {
            throw new RestApiException(Message.SINH_VIEN_NOT_EXISTS);
        }

        String nhomDatnId = request.getNhomDatnId();
        NhomDatn nhomDatn = nhomDatnRepository.findById(nhomDatnId).orElse(null);
        if (nhomDatn == null) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }

        sinhVien.setNhomId(nhomDatnId);
        sinhVien.setMonDatnId(request.getMonDatnId());
        sinhVien.setThoiDiemJoinNhomGanNhat(Calendar.getInstance().getTimeInMillis());

        return sinhVienRepository.save(sinhVien);
    }

    public void validate(ThamGiaNhomDatnAuthRequest request) {

    }

}
