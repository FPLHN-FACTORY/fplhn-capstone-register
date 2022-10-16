package com.fpolydatn.core.daotao.giangvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.daotao.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.daotao.giangvien.model.request.GvChotNhomDatnRequest;
import com.fpolydatn.core.daotao.giangvien.model.request.GvFindNhomDatnHuongDanRequest;
import com.fpolydatn.core.daotao.giangvien.model.request.GvUpdateTenDeTaiRequest;
import com.fpolydatn.core.daotao.giangvien.model.request.GvUpdateNhomDatnRequest;
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

    PageableObject<GvNhomDatnResponse> findAllNhomDatnDoiPheDuyet(String maGvhd, final GvFindNhomDatnHuongDanRequest request);

    NhomDatn pheDuyetNhomDatn(final GvChotNhomDatnRequest request);

    PageableObject<GvNhomDatnResponse> findAllNhomDatnHuongDan(String maGvhd, final GvFindNhomDatnHuongDanRequest request);

    NhomDatn pheDuyetDeTaiNhomDatn(final GvChotNhomDatnRequest request);

    NhomDatn tuChoiDeTaiNhomDatn(final GvChotNhomDatnRequest request);

}
