package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenNhomRequest;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Service
public final class SuaTenNhomUseCase implements IUseCase<SvUpdateTenNhomRequest, NhomDatn> {

    private final NhomDatnRepository repository;

    @Autowired
    public SuaTenNhomUseCase(
            @Qualifier(NhomDatnRepository.NAME) NhomDatnRepository repository) {
        this.repository = repository;
    }

    @Override
    public NhomDatn execute(SvUpdateTenNhomRequest request) {
        if ("".equals(request.getTenNhom())) {
            throw new RestApiException("...");
        }

        NhomDatn nhomDatn = repository.findById(request.getNhomDatnId()).orElse(null);
        if (nhomDatn == null) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!request.getUserId().equals(nhomDatn.getTruongNhomId())) {
            throw new RestApiException(Message.KHONG_PHAI_TRUONG_NHOM);
        }
        nhomDatn.setTenDeTai(request.getTenNhom());
        return repository.save(nhomDatn);
    }
}
