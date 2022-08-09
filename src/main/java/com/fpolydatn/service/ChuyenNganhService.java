package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateChuyenNganhRequest;
import com.fpolydatn.model.ChuyenNganh;

import java.util.UUID;

/**
 * @author Vinhnv
 */
public interface ChuyenNganhService {

    ChuyenNganh insert(CreateChuyenNganhRequest craCreateChuyenNganhRequest);

    ChuyenNganh search(UUID id);

}
