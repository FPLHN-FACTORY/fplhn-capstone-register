package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.infrastructure.request.CreateHocKyRequet;
import com.fpolydatn.model.HocKy;

import java.util.List;
import java.util.UUID;

/**
 * @author vinhnv
 */
public interface HocKyService {
    List<HocKy>getList(int offset, int size);
    HocKy addNew(CreateHocKyRequet hocKy);

    String delete(UUID id);
}
