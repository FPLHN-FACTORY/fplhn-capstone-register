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
    private SvSinhVienRepository svSinhVienRepository;

    @Override
    public List<SvSinhVienTheoNhomResponse> findByNhomId(final SvFindSinhVienRequest request) {
        return svSinhVienRepository.findByNhomId(request);
    }

    @Override
    public String getNhomIdById(String coSoId, String dotDangKyId, String id) {
        return svSinhVienRepository.getNhomIdById(coSoId, dotDangKyId, id);
    }

    @Override
    public Integer getSoThanhVienByNhomId(String nhomId) {
        return svSinhVienRepository.getSoLuongThanhVienById(nhomId);
    }

    @Override
    public String getMonDatnIdBySinhVienId(String id) {
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(id);
        return sinhVien.get().getMonDatnId();
    }


}
