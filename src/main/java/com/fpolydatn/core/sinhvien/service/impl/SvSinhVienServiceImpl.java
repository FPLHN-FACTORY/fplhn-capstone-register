package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienTheoNhomResponse;
import com.fpolydatn.core.sinhvien.repository.SvNhomDatnRepository;
import com.fpolydatn.core.sinhvien.repository.SvSinhVienRepository;
import com.fpolydatn.core.sinhvien.service.SvSinhVienService;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author thangdt
 */

@Service
public class SvSinhVienServiceImpl implements SvSinhVienService {

    @Autowired
    private SvSinhVienRepository sinhVienRepository;

    @Autowired
    private SvNhomDatnRepository svNhomDatnRepository;

    @Override
    public List<SvSinhVienTheoNhomResponse> findByNhomId(final SvFindSinhVienRequest request) {
        return sinhVienRepository.findByNhomId(request);
    }

    @Override
    public String getNhomIdById(String coSoId, String dotDangKyId, String id) {
        return sinhVienRepository.getNhomIdById(coSoId, dotDangKyId, id);
    }

    @Override
    public SinhVien saveNhomDatn(String sinhVienId, String nhomDatnId) {
        Optional<SinhVien> sinhVienOptional = sinhVienRepository.findById(sinhVienId);
        if (!sinhVienOptional.isPresent()) {
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        Optional<NhomDatn> nhomDatnOptional = svNhomDatnRepository.findById(nhomDatnId);
        if (!nhomDatnOptional.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        SinhVien sinhVien = sinhVienOptional.get();
        sinhVien.setNhomId(nhomDatnId);
        return sinhVienRepository.save(sinhVien);
    }

}
