package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.repository.DtSinhVienRepository;
import com.fpolydatn.core.daotao.service.DtSinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author thepvph20110
 */
@Service
public class DtSinhVienServiceImpl implements DtSinhVienService {

    @Autowired
    private DtSinhVienRepository sinhVienRepository;

    @Override
    public PageableObject<DtSinhVienResponse> findAllSinhVien(final FindDtSinhVienRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtSinhVienResponse> res = sinhVienRepository.findAllSinhVien(pageable,request);
        return new PageableObject(res);
    }

    @Override
    public PageableObject<DtSinhVienResponse> searchStudent(final FindDtSinhVienRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtSinhVienResponse> res = sinhVienRepository.searchStudent(request,pageable);
        return new PageableObject<>(res);
    }
}
