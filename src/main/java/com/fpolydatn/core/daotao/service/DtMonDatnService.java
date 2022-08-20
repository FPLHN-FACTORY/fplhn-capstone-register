package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.CreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.FindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.UpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.MonDatnResponse;

import com.fpolydatn.entity.MonDatn;

import javax.validation.Valid;


/**
 * @author hungpv
 */
public interface DtMonDatnService {
    PageableObject<MonDatnResponse> search(@Valid final FindMonDatnRequest req);

    Boolean delete(final String id);

    MonDatn findById(String id);

    MonDatn update(@Valid final UpdateMonDatnRequest command);

    Boolean add(@Valid final CreateMonDatnRequest command);
}
