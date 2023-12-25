package com.fpolydatn.core.admin.service;

import com.fpolydatn.core.admin.repository.AdHocKyRepository;
import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author phongtt35
 */
@Service
public final class XoaHocKyUseCase implements IUseCase<String, Boolean> {

    private final AdHocKyRepository repository;

    @Autowired
    public XoaHocKyUseCase(AdHocKyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Boolean execute(final String hocKyId) {
        if (!repository.existsById(hocKyId)) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        Long dotDangKyTrongHocKy = repository.countDotDangKy(hocKyId);
        if (dotDangKyTrongHocKy > 0) {
            throw new RestApiException(Message.TON_TAI_DOT_DANG_KY);
        }
        repository.deleteById(hocKyId);
        return true;
    }
}
