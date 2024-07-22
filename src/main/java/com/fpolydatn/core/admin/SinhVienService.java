package com.fpolydatn.core.admin;

import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.repository.SinhVienRepository;
import org.apache.commons.math3.analysis.function.Sinh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Service
public class SinhVienService {

    @Autowired
    @Qualifier(SinhVienRepository.NAME)
    private SinhVienRepository sinhVienRepository;

    public Page<SinhVien> getAllSinhViens(Pageable pageable) {
        return sinhVienRepository.findAll(pageable);
    }

    public Page<SinhVien> getSinhViensByCoSoIdAndDotDangKyId(String coSoId, String dotDangKyId, Pageable pageable) {
        return sinhVienRepository.findByCoSoIdAndDotDangKyId(coSoId, dotDangKyId, pageable);
    }

    public Optional<SinhVien> getSinhVienById(String id) {
        return sinhVienRepository.findById(id);
    }

    public SinhVien saveSinhVien(SinhVien sinhVien) {
        return sinhVienRepository.save(sinhVien);
    }

    public void deleteSinhVien(String id) {
        sinhVienRepository.deleteById(id);
    }
}