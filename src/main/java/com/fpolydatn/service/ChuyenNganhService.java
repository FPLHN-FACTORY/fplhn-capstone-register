package com.fpolydatn.service;

import com.fpolydatn.infrastructure.command.chuyennganh.CreateChuyenNganhCommand;
import com.fpolydatn.model.domain.ChuyenNganh;
import com.fpolydatn.model.entity.ChuyenNganhEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Vinhnv
 */
public interface ChuyenNganhService {

    ChuyenNganhEntity insert(CreateChuyenNganhCommand createChuyenNganhRequest);

    ChuyenNganhEntity search(UUID id);

    List<ChuyenNganh> getListChuyenNganhCustom();

}
