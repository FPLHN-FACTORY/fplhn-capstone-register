package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.repository.SvNhomDatnRepository;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.repository.SinhVienRepository;
import org.apache.commons.math3.analysis.function.Sinh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Service
public final class LayDsNhomDatnUseCase implements IUseCase<SvFindNhomDatnRequest, PageableObject<SvNhomDatnResponse>> {

    private final SvNhomDatnRepository svNhomDatnRepository;
    private final SinhVienRepository sinhVienRepository;
    private final FpolyDatnSession session;

    @Autowired
    public LayDsNhomDatnUseCase(SvNhomDatnRepository svNhomDatnRepository,
                                SinhVienRepository sinhVienRepository,
                                FpolyDatnSession session) {
        this.svNhomDatnRepository = svNhomDatnRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.session = session;
    }

    @Override
    public PageableObject<SvNhomDatnResponse> execute(SvFindNhomDatnRequest request) {
        String sinhVienId = session.getUserId();
        String nhomMonId = sinhVienRepository.getNhomMonIdOfSinhVien(sinhVienId);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<SvNhomDatnResponse> res = svNhomDatnRepository.findAllNhomDatn(pageable, request, nhomMonId);
        return new PageableObject(res);
    }
}
