package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtDetailNhomMonResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.entity.MonDatn;

import javax.validation.Valid;
import java.util.List;

public interface DtNhomMonDatnService {

    PageableObject<DtNhomMonDatnResponse> findAllByCoSo(DtFindNhomMonDatnRequest req);

    Boolean delete(final String id, String coSoId);

    Boolean update(@Valid final DtUpdateNhomMonDatnRequest command);

    Boolean add(@Valid final DtCreateNhomMonDatnRequest command);

    MonDatn findById(String id);

    List<DtDetailNhomMonResponse> detail(String id, String coSoId);
}
