package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.HocKyRequest;
import com.fpolydatn.model.domain.HocKy;

import java.util.List;
import java.util.UUID;

/**
 * @author HangNT
 */
public interface HocKyService {

    HocKy addNew(HocKyRequest hocKy);

    Boolean delete(UUID id);

    HocKy update(UUID id, HocKyRequest hocKy);

    HocKy findById(UUID id);

    long getSizeListHocKy(String tenHocKy);

    List<HocKy> searchHocKy(String tenHocKy, int offset, int size);

}
