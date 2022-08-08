package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.model.HocKy;

import java.util.List;
import java.util.UUID;

/**
 * @author vinhnv
 */
public interface HocKyService {

    List<HocKy> getList(int offset, int size);

    HocKy addNew(CreateHocKyRequest hocKy);

    void delete(UUID id);

    HocKy update(UUID id, HocKy hocKy);

    HocKy findById(UUID id);

    long getSizeListHocKy();

}
