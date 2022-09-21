package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.repository.DtNhomDatnTheoDotRepository;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DtNhomDatnTheoDotServiceImpl implements DtNhomDatnTheoDotService {
    @Autowired
    private DtNhomDatnTheoDotRepository dtNhomDatnTheoDotRepository;
    @Override
    public PageableObject<DtNhomDatnTheoDotResponse> findAllNhomDatn(FindDtNhomDatnTheoDotRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtNhomDatnTheoDotResponse> res = dtNhomDatnTheoDotRepository.findAllNhomDatn(pageable,request);
        return new PageableObject(res);
    }
    @Override
    public PageableObject<DtSinhVienResponse> showDanhSachSinhVien(final FindSinhVienRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        System.out.println(dtNhomDatnTheoDotRepository.showDanhSachSinhVien(pageable,request).getSize());
        Page<DtSinhVienResponse> res = dtNhomDatnTheoDotRepository.showDanhSachSinhVien(pageable,request);
        return new PageableObject<>(res);
    }

}
