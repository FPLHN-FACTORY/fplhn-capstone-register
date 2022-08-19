package com.fpolydatn.core.admin.service;

import com.fpolydatn.core.admin.model.request.CreateHocKyRequest;
import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.request.UpdateHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.model.entity.HocKyEntity;

import javax.validation.Valid;

/**
 * @author HangNT
 */

public interface HocKyService {

    HocKyEntity create(@Valid final CreateHocKyRequest command);

    Boolean delete(final String id);

    HocKyEntity update(@Valid final UpdateHocKyRequest command);

    HocKyEntity findById(final String id);

    long getSizeListHocKy(String tenHocKy);

    PageableObject<HocKyResponse> search(final FindHocKyRequest req);

}
