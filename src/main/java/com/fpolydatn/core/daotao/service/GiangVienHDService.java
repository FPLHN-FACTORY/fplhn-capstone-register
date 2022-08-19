package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.CreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.FindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.UpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.GiangVienHDResponse;
import com.fpolydatn.entity.GiangVienHuongDan;


import javax.validation.Valid;

/**
 * @author Vinhnv
 */
public interface GiangVienHDService {

    GiangVienHuongDan create(@Valid final CreateGiangVienHDRequest command);

    Boolean delete(final String id);

    GiangVienHuongDan update(@Valid final UpdateGiangVienHDRequest command);

    GiangVienHuongDan findById(final String id);

    long getSizeListHocKy(String tenGiangVien);

    PageableObject<GiangVienHDResponse> search(final FindGiangVienHDRequest req);
}
