package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienTheoNhomResponse;
import com.fpolydatn.core.sinhvien.repository.SvSinhVienRepository;
import com.fpolydatn.core.sinhvien.service.SvSinhVienService;
import com.fpolydatn.entity.SinhVien;
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
    public String getNhomIdById(String coSoId, String dotDangKyId, String id) {
        return svSinhVienRepository.getNhomIdById(coSoId, dotDangKyId, id);
    }

    @Override
    public Integer getSoThanhVienByNhomId(String nhomId, String coSoId) {
        return svSinhVienRepository.getSoLuongThanhVienById(nhomId, coSoId);
    }

    @Override
    public String getMonDatnIdBySinhVienId(String id) {
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(id);
        return sinhVien.get().getMonDatnId();
    }

    @Override
    public String getMonChuongTrinhIdById(String id) {
//        return svSinhVienRepository.getMonChuongTrinhIdById(id);
        return null;
    }


}
