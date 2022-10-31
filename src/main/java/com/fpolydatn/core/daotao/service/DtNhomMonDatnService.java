package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtCreateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.entity.MonDatn;

import javax.validation.Valid;

public interface DtNhomMonDatnService {
    PageableObject<DtNhomMonDatnResponse> search(@Valid final DtFindNhomMonDatnRequest req);

    Boolean delete(final String id);

    Boolean update(@Valid final DtUpdateNhomMonDatnRequest command);

    Boolean add(@Valid final DtCreateNhomMonDatnRequest command);

    MonDatn getNhomMonDatnById(String id, String coSoId);
}
