package com.fpolydatn.service;

import com.fpolydatn.infrastructure.command.chuyennganh.CreateChuyenNganhCommand;
import com.fpolydatn.model.domain.ChuyenNganh;
import com.fpolydatn.model.entity.ChuyenNganhEntity;

import java.util.List;

/**
 * @author Vinhnv
 */
public interface ChuyenNganhService {

    ChuyenNganhEntity insert(CreateChuyenNganhCommand createChuyenNganhRequest);

    ChuyenNganhEntity search(String id);

    List<ChuyenNganh> getListChuyenNganhCustom();

}
