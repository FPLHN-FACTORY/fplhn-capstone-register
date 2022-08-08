package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateDotDangKyRequest;
import com.fpolydatn.model.DotDangKy;

import java.util.List;
import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface DotDangKyService {

    DotDangKy deleteDotDangKy(UUID id);

    List<DotDangKy> getListDotDangKy(UUID coSoId, int offset, int size);

    DotDangKy findByID(UUID id);

}
