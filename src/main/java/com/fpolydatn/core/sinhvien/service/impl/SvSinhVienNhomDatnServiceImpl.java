package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.sinhvien.model.request.CreateSinhVienJoinNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;
import com.fpolydatn.core.sinhvien.repository.SvSinhVienNhomDatnRepository;
import com.fpolydatn.core.sinhvien.service.SvSinhVienNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class SvSinhVienNhomDatnServiceImpl implements SvSinhVienNhomDatnService {
    @Autowired
    private SvSinhVienNhomDatnRepository svSinhVienNhomDatnRepository;

    @Override
    public PageableObject<SvSinhVienNhomDatnResponse> findAllNhomDatn(FindDtNhomDatnTheoDotRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<SvSinhVienNhomDatnResponse> res = svSinhVienNhomDatnRepository.findAllNhomDatn(pageable, request);
        return new PageableObject(res);
    }

    @Override
    public PageableObject<DtSinhVienResponse> showDanhSachSinhVien(final FindSinhVienRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        System.out.println(svSinhVienNhomDatnRepository.showDanhSachSinhVien(pageable, request).getSize());
        Page<DtSinhVienResponse> res = svSinhVienNhomDatnRepository.showDanhSachSinhVien(pageable, request);
        return new PageableObject<>(res);
    }

    @Override
    public boolean addSinhVienTheoNhom(final CreateSinhVienJoinNhomDatnRequest req) {
        svSinhVienNhomDatnRepository.addSinhVienInGroup(req.getIdNhom(), "d12715e8-df64-4f4a-9b55-29c491f1f92a");
        return true;
    }

}
