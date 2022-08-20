package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.CreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.FindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.UpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.MonDatnResponse;

import com.fpolydatn.entity.MonDatn;


/**
 * @author hungpv
 */
public interface MonDatnService {
    PageableObject<MonDatnResponse> search(final FindMonDatnRequest req);

    Boolean deleteMonDatn(final String id);

    MonDatn findById(String id);

    MonDatn update(final UpdateMonDatnRequest command);

    Boolean create(final CreateMonDatnRequest command);
}
