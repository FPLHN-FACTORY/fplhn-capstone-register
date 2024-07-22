package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.daotao.model.request.CreateHocKyRequest;
import com.fpolydatn.core.daotao.model.request.FindHocKyRequest;
import com.fpolydatn.core.daotao.model.request.UpdateHocKyRequest;
import com.fpolydatn.core.daotao.model.response.HocKyResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.HocKy;

import javax.validation.Valid;
import java.util.List;

/**
 * @author HangNT
 */

public interface HocKyService {

    HocKy create(@Valid final CreateHocKyRequest command);

    Boolean delete(final String id);

    HocKy update(@Valid final UpdateHocKyRequest command);

    HocKy findById(final String id);

    long getSizeListHocKy(String tenHocKy);

    PageableObject<HocKyResponse> search(final FindHocKyRequest req);

    List<HocKy> getAll();

}
