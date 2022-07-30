package com.fpolydatn.service;

import com.fpolydatn.model.ChuyenNganh;

import java.util.UUID;

/**
 * @author Vinhnv
 */
public interface ChuyenNganhService {

    Object getList (UUID id, int offset , int size);
    ChuyenNganh searchID(UUID id);
}
