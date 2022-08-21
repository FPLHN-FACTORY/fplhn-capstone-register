package com.fpolydatn.core.admin.service.daotao.service;

import com.fpolydatn.core.admin.service.daotao.model.request.DtCreateChuyenNganhRequest;
import com.fpolydatn.core.admin.service.daotao.model.request.DtFindChuyenNganhRequest;
import com.fpolydatn.core.admin.service.daotao.model.request.DtUpdateChuyenNganhRequest;
import com.fpolydatn.core.admin.service.daotao.model.response.DtChuyenNganhResponse;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.ChuyenNganh;
import javax.validation.Valid;
import java.util.List;

/**
 * @author hungpv
 */
public interface DtChuyenNganhService {

    List<SimpleEntityProj> findAllSimpleEntityByCoSo(String id);

    ChuyenNganh update(@Valid final DtUpdateChuyenNganhRequest req);

    ChuyenNganh add(@Valid final DtCreateChuyenNganhRequest req);
    Boolean delete(String id);

    ChuyenNganh findById(String id);

    PageableObject<DtChuyenNganhResponse> search(final DtFindChuyenNganhRequest req);
}
