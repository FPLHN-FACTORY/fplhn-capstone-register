package com.fpolydatn.core.giangvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.GvChotNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateTenDeTaiRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.entity.NhomDatn;

import javax.validation.Valid;


/**
 * @author thangdt
 */


public interface GvNhomDatnService {

    PageableObject<GvNhomDatnResponse> fillAllNhomDatn(final GvFindNhomDatnRequest request);

    NhomDatn updateTrangThaiNhom(final GvUpdateNhomDatnRequest request);

    NhomDatn findById(String maNhom);

    NhomDatn updateTenDeTai(@Valid final GvUpdateTenDeTaiRequest request);

    PageableObject<GvNhomDatnResponse> findAllNhomDatnGroupCanPheDuyet(String maGvhd, final GvNhomDatnRequest request);

    NhomDatn pheDuyetNhomDatn(final GvChotNhomDatnRequest request);

    PageableObject<GvNhomDatnResponse> findAllNhomDatnHuongDan(String maGvhd, final GvNhomDatnRequest request);

    NhomDatn pheDuyetDeTaiNhomDatn(final GvChotNhomDatnRequest request);
}
