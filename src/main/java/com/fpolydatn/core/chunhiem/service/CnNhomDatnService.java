package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.NhomDatn;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author thangncph26123
 */
public interface CnNhomDatnService {

    PageableObject<CnNhomDatnResponse> search(final CnFindNhomDatnRequest req);

    boolean updateTrangThaiNhom(String id, String coSoId);

    Boolean delete(String id);

    NhomDatn findById(String id);

}