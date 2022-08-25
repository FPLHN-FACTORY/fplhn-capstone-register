package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.entity.GiangVienHuongDan;

import javax.validation.Valid;

/**
 * @author Vinhnv
 */
public interface DtGiangVienHDService {

    GiangVienHuongDan create(@Valid final DtCreateGiangVienHDRequest command);

    Boolean delete(final String id);

    GiangVienHuongDan update(@Valid final DtUpdateGiangVienHDRequest command);

    GiangVienHuongDan findById(final String id);

    long getSizeListHocKy(String tenGiangVien);

    PageableObject<DtGiangVienHDResponse> search(final DtFindGiangVienHDRequest req);

}
