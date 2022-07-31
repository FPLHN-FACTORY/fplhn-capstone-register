package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateDotDangKyRequest;
import com.fpolydatn.infrastructure.response.DotDangKyResponse;

import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface DotDangKyService {

    DotDangKyResponse createDotDangKy(CreateDotDangKyRequest dotDangKyRequest);

    DotDangKyResponse updateDotDangKy(CreateDotDangKyRequest dotDangKyRequest, UUID id);

}
