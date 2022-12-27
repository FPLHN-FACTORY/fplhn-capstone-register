package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.repository.SvSinhVienRepository;
import com.fpolydatn.core.sinhvien.service.SvSinhVienService;
import com.fpolydatn.entity.SinhVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Integer getSoThanhVienByNhomId(String nhomId, String coSoId, String dotDangKyId) {
        return svSinhVienRepository.getSoLuongThanhVienById(nhomId, coSoId, dotDangKyId);
    }

    @Override
    public String getMonDatnIdBySinhVienId(String id) {
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(id);
        return sinhVien.get().getMonDatnId();
    }

}
