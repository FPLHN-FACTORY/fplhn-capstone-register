package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.NhomDatn;

import javax.validation.Valid;

/**
 * @author thangncph26123
 */
public interface CnNhomDatnService {

    PageableObject<CnNhomDatnResponse> search(final CnFindNhomDatnRequest req);

    boolean chotDeTai(String id);

    boolean tuChoiChotDeTai(String id, String nhanXet);

    Boolean delete(String id);

    NhomDatn findById(String id);

    boolean create(@Valid final CnCreateNhomDatnRequest cnCreateNhomDatnRequest);

}