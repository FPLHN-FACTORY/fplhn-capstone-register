package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.hocky.CreateHocKyRequest;
import com.fpolydatn.infrastructure.request.hocky.UpdateHocKyRequest;
import com.fpolydatn.model.domain.HocKy;

import java.util.List;
import java.util.UUID;

/**
 * @author HangNT
 */
public interface HocKyService {

    HocKy addNew(CreateHocKyRequest hocKy);

    Boolean delete(UUID id);

    HocKy update(UUID id, UpdateHocKyRequest hocKy);

    HocKy findById(UUID id);

    long getSizeListHocKy(String tenHocKy);

    List<HocKy> searchHocKy(String tenHocKy, int offset, int size);

}
