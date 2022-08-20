package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtFindDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.response.DtDotDangKyResponse;
import com.fpolydatn.entity.DotDangKy;

import javax.validation.Valid;

public interface DtDotDangKyService {

    PageableObject<DtDotDangKyResponse> findByCoSo(DtFindDotDangKyRequest request);

    DotDangKy create(@Valid DtCreateDotDangKyRequest createDotDangKyRequest);

    DotDangKy update(DtUpdateDotDangKyRequest dtUpdateDotDangKyRequest);

    DotDangKy findById(String id);
}
