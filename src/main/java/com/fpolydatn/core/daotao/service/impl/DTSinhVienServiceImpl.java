package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDTSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DTSinhVienResponse;
import com.fpolydatn.core.daotao.repository.DTSinhVienRepository;
import com.fpolydatn.core.daotao.service.DTSinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class DTSinhVienServiceImpl implements DTSinhVienService {

    @Autowired
    private DTSinhVienRepository sinhVienRepository;

    @Override
    public PageableObject<DTSinhVienResponse> findAllSinhVien(final FindDTSinhVienRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DTSinhVienResponse> res = sinhVienRepository.findAllSinhVien(pageable,request);
        return new PageableObject(res);
    }

    @Override
    public PageableObject<DTSinhVienResponse> searchStudent(final FindDTSinhVienRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DTSinhVienResponse> res = sinhVienRepository.searchStudent(request,pageable);
        return new PageableObject<>(res);
    }
}
