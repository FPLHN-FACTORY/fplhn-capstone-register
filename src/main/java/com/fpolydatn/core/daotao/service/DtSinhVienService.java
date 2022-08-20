package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;

/**
 * @author thepvph20110
 */
public interface DtSinhVienService {

    PageableObject<DtSinhVienResponse> findAllSinhVien(final FindDtSinhVienRequest request);

    PageableObject<DtSinhVienResponse> searchStudent(final FindDtSinhVienRequest request);

}
