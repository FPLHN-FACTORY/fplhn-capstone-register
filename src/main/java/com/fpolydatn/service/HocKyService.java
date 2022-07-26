package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.model.HocKy;

import java.util.List;

/**
 * @author vinhnv
 */
public interface HocKyService {
    List<HocKy>getList(int offset, int size);
    HocKy addNew(CreateHocKyRequest hocKy);
}
