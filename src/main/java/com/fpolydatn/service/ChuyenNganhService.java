package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.chuyennganh.CreateChuyenNganhRequest;
import com.fpolydatn.model.domain.ChuyenNganh;

import java.util.UUID;

/**
 * @author Vinhnv
 */
public interface ChuyenNganhService {

    ChuyenNganh insert(CreateChuyenNganhRequest createChuyenNganhRequest);

    ChuyenNganh search(UUID id);

}
