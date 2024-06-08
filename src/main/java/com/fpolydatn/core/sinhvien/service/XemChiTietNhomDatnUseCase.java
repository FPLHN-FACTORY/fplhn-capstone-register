package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.repository.SvNhomDatnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PhongTT35
 * @description Chưa rõ lắm việc sinh viên xem chi tiết nhóm để làm gì
 **/
@Service
public final class XemChiTietNhomDatnUseCase implements IUseCase<String, SvDetailNhomDatnResponse> {

    private final SvNhomDatnRepository repository;

    @Autowired
    public XemChiTietNhomDatnUseCase(SvNhomDatnRepository repository) {
        this.repository = repository;
    }

    @Override
    public SvDetailNhomDatnResponse execute(String id) {
        // Cần sửa
        return repository.getDetailNhomDatnById(id).get(0);
    }
}
