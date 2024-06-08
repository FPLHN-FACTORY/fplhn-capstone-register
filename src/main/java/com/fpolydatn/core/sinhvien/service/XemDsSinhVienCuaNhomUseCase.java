package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Service
public final class XemDsSinhVienCuaNhomUseCase implements IUseCase<String, List<SvShowSinhVienTheoNhomResponse>> {

    private final NhomDatnRepository nhomDatnRepository;

    @Autowired
    public XemDsSinhVienCuaNhomUseCase(NhomDatnRepository nhomDatnRepository) {
        this.nhomDatnRepository = nhomDatnRepository;
    }

    @Override
    public List<SvShowSinhVienTheoNhomResponse> execute(String nhomDatnId) {
        return nhomDatnRepository.getDsSinhVienTrongNhom(nhomDatnId);
    }
}
