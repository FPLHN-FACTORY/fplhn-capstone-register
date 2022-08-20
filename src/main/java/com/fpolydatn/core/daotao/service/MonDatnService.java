package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.admin.model.request.CreateHocKyRequest;
import com.fpolydatn.core.admin.model.request.UpdateHocKyRequest;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.CreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.FindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.UpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.MonDatnResponse;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.entity.MonDatn;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author hungpv
 */
public interface MonDatnService {
    PageableObject<MonDatnResponse>search(final FindMonDatnRequest req);

    long getSizeListMonDatn(String tenMonDatn);

    Boolean deleteMonDatn(final String id);

    MonDatn findById(String id);

    MonDatn update(@Valid final UpdateMonDatnRequest command);

    MonDatn create(@Valid final CreateMonDatnRequest command);
}
