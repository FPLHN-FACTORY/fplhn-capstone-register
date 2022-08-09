package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.model.HocKy;

import java.util.List;
import java.util.UUID;

/**
 * @author vinhnv
 */
public interface HocKyService {

    HocKy addNew(CreateHocKyRequest hocKy);

    void delete(UUID id);

    HocKy update(UUID id, HocKy hocKy);

    HocKy findById(UUID id);

    long getSizeListHocKy(String tenHocKy);

    List<HocKy> searchHocKy(String tenHocKy, int offset, int size);

}
