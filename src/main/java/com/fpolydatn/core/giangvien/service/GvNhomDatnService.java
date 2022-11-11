package com.fpolydatn.core.giangvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.GvChotNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateTenDeTaiRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.entity.NhomDatn;

import javax.validation.Valid;

/**
 * @author thangdt
 */

public interface GvNhomDatnService {

    PageableObject<GvNhomDatnResponse> fillAllNhomDatn(final GvFindNhomDatnRequest request);

    NhomDatn findById(String maNhom);

    NhomDatn updateTenDeTai(@Valid final GvUpdateTenDeTaiRequest request);

    NhomDatn pheDuyetNhomDatn(final GvChotNhomDatnRequest request);

    NhomDatn chotDeTaiNhomDatn(final GvChotNhomDatnRequest request);

    boolean mocThoiGianGiangVienHuongDan();

}
