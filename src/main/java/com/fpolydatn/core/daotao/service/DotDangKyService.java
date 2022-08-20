package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.daotao.model.request.CreateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.UpdateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.response.DotDangKyResponse;
import com.fpolydatn.entity.DotDangKy;

import java.util.List;
import java.util.UUID;

public interface DotDangKyService {

    DotDangKy deleteDotDangKy(UUID id);

    List<DotDangKy> getListDotDangKy(UUID coSoId, int offset, int size);

    DotDangKy addNew(CreateDotDangKyRequest dotDangKyRequest);

    List<DotDangKyResponse> findDotDangKyByCoSo(UUID id, int offset, int size);

    DotDangKy findById(UUID id);

    DotDangKy update(UUID id, UpdateDotDangKyRequest request);

    Long countDotDangKyByCoSo(UUID coSoId);

    List<DotDangKy> findAllByCoSo(UUID coSoId);
}
