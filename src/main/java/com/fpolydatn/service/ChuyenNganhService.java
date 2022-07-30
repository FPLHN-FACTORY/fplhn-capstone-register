package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateChuyenNganhRequest;
import com.fpolydatn.model.ChuyenNganh;

/**
 * @author Vinhnv
 */
public interface ChuyenNganhService {
    ChuyenNganh insert(CreateChuyenNganhRequest craCreateChuyenNganhRequest);

}
