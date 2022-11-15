package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtBaseDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtCreateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtFindDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.response.DtDetailDotDangKyResponse;
import com.fpolydatn.core.daotao.model.response.DtDotDangKyResponse;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import javax.validation.Valid;
import java.util.List;

/**
 * @author SonPT
 */

public interface DtDotDangKyService {

    PageableObject<DtDotDangKyResponse> findByCoSo(DtFindDotDangKyRequest request);

    DotDangKy create(@Valid final DtCreateDotDangKyRequest createDotDangKyRequest);

    DotDangKy update(@Valid final DtUpdateDotDangKyRequest dtUpdateDotDangKyRequest);

    DotDangKy findById(String id);

    List<SimpleEntityProj> findAllSimpleEntity(String coSoId);

    List<SimpleEntityProj> findAllSimpleEntityByCoSo(String coSoId);

    List<DotDangKy> getAllByCoSo(String coSoId);

    DtDetailDotDangKyResponse detail(String id, String coSoId);

    DotDangKy checkDateDotDangKy(DtBaseDotDangKyRequest request, String id, String coSoId);

}