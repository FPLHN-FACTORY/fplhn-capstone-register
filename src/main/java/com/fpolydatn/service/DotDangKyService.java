package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.dotdangky.CreateDotDangKyRequest;
import com.fpolydatn.infrastructure.response.dotdangky.DotDangKyResponse;
import com.fpolydatn.model.domain.DotDangKy;

import java.util.List;
import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface DotDangKyService {

    DotDangKy deleteDotDangKy(UUID id);

    List<DotDangKy> getListDotDangKy(UUID coSoId, int offset, int size);

    DotDangKy findByID(UUID id);

    DotDangKyResponse createDotDangKy(CreateDotDangKyRequest dotDangKyRequest);

    DotDangKyResponse updateDotDangKy(CreateDotDangKyRequest dotDangKyRequest, UUID id);

}
