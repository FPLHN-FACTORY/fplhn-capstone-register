package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.CreateChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.request.FindChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.request.UpdateChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.response.ChuyenNganhResponse;
import com.fpolydatn.entity.ChuyenNganh;
import javax.validation.Valid;

public interface ChuyenNganhService {

    ChuyenNganh update(final UpdateChuyenNganhRequest req);

    ChuyenNganh add(@Valid final CreateChuyenNganhRequest req);
    Boolean delete(String id);

    ChuyenNganh findById(String id);

    PageableObject<ChuyenNganhResponse> search(final FindChuyenNganhRequest req);
}
