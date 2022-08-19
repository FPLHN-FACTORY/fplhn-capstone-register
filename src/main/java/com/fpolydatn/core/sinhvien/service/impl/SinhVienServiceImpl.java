package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.model.request.FindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.NhomSinhVienResponse;
import com.fpolydatn.core.sinhvien.repository.SvSinhVienRepository;
import com.fpolydatn.core.sinhvien.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thangdt
 */

@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SvSinhVienRepository sinhVienRepository;

    @Override
    public List<NhomSinhVienResponse> findByNhomId( FindSinhVienRequest request) {
        return sinhVienRepository.findByNhomId(request);
    }
}
