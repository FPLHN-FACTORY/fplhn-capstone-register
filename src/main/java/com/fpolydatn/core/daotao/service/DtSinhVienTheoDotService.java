package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface DtSinhVienTheoDotService {

    PageableObject<DtSinhVienTheoDotResponse> findAllSinhVien(final FindDtSinhVienTheoDotRequest request);

    PageableObject<DtSinhVienTheoDotResponse> searchStudent(final FindDtSinhVienTheoDotRequest request);

}
