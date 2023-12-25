package com.fpolydatn.core.admin.service;

import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.admin.repository.AdDotDangKyRepository;
import com.fpolydatn.core.admin.repository.AdHocKyRepository;
import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.entity.HocKy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : PhongTT35
 **/
@Service
public final class XemChiTietHocKyUseCase implements IUseCase<String, HocKy> {

    private final AdHocKyRepository repository;

    @Autowired
    public XemChiTietHocKyUseCase(AdHocKyRepository repository) {
        this.repository = repository;
    }

    @Override
    public HocKy execute(String id) {
        return repository.findById(id).orElse(null);
    }
}
