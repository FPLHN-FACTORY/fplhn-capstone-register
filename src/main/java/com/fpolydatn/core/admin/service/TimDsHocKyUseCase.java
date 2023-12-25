package com.fpolydatn.core.admin.service;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.admin.repository.AdDotDangKyRepository;
import com.fpolydatn.core.admin.repository.AdHocKyRepository;
import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.common.base.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author : PhongTT35
 **/
@Service
public final class TimDsHocKyUseCase implements IUseCase<FindHocKyRequest, PageableObject<HocKyResponse>> {

    private final AdHocKyRepository repository;

    @Autowired
    public TimDsHocKyUseCase(AdHocKyRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageableObject<HocKyResponse> execute(final FindHocKyRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<HocKyResponse> res = repository.findByName(request, pageable);
        return new PageableObject(res);
    }
}
