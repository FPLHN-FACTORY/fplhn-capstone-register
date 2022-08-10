package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.hocky.CreateHocKyRequest;
import com.fpolydatn.infrastructure.request.hocky.UpdateHocKyRequest;
import com.fpolydatn.model.domain.HocKy;

import java.util.List;
import java.util.UUID;

/**
 * @author vinhnv
 */
public interface HocKyService {

    HocKy searchById(UUID id);

    List<HocKy> getList(int offset, int size);

    HocKy addNew(CreateHocKyRequest hocKy);

    String delete(UUID id);

    HocKy update(UUID id, UpdateHocKyRequest updateHocKyRequest);
}
