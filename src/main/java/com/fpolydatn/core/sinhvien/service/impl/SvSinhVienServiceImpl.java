package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomSinhVienResponse;
import com.fpolydatn.core.sinhvien.repository.SvSinhVienRepository;
import com.fpolydatn.core.sinhvien.service.SvSinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thangdt
 */

@Service
public class SvSinhVienServiceImpl implements SvSinhVienService {

    @Autowired
    private SvSinhVienRepository sinhVienRepository;

    @Override
    public List<SvNhomSinhVienResponse> findByNhomId(final SvFindSinhVienRequest request) {
        return sinhVienRepository.findByNhomId(request);
    }
}
