package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.NhomDatn;
import org.springframework.data.repository.query.Param;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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