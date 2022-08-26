package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.core.daotao.repository.DtSinhVienTheoDotRepository;
import com.fpolydatn.core.daotao.service.DtSinhVienTheoDotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author thepvph20110
 */
@Service
public class DtSinhVienTheoDotServiceImpl implements DtSinhVienTheoDotService {

    @Autowired
    private DtSinhVienTheoDotRepository sinhVienRepository;

    @Autowired
    private DtDotDangKyRepository dotDangKyRepository;

    @Override
    public PageableObject<DtSinhVienTheoDotResponse> findAllSinhVien(final FindDtSinhVienTheoDotRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtSinhVienTheoDotResponse> res = sinhVienRepository.findAllSinhVien(pageable,request);
        return new PageableObject(res);
    }

    @Override
    public PageableObject<DtSinhVienTheoDotResponse> searchStudent(final FindDtSinhVienTheoDotRequest request) {
        if(request.getIdDotDangKy().equalsIgnoreCase("")){
            request.setIdDotDangKy(dotDangKyRepository.DotDangKyHienTai().getId());
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtSinhVienTheoDotResponse> res = sinhVienRepository.searchStudent(request,pageable);
        return new PageableObject<>(res);
    }


}
