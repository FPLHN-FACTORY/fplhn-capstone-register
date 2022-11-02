package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;

import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


/**
 * @author hungpv
 */
public interface DtMonDatnService {
    PageableObject<DtMonDatnResponse> search(@Valid final DtFindMonDatnRequest req);

    Boolean delete(final String id, String coSoId, String dotDangKyId);

    MonDatn findById(String id);

    List<MonDatn> findByTenNhomMon();

    MonDatn update(@Valid final DtUpdateMonDatnRequest command);

    Boolean add(@Valid final DtCreateMonDatnRequest command);

    List<String> importExcel(MultipartFile file);

}
