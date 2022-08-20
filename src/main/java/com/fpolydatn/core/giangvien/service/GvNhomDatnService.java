package com.fpolydatn.core.giangvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author thangdt
 */


public interface GvNhomDatnService {

    PageableObject<GvNhomDatnResponse> fillAllNhomDatn(final GvFindNhomDatnRequest request);

    NhomDatn updateTrangThaiNhom(final GvUpdateNhomDatnRequest request);

    List<SimpleEntityProj> getListDotDangKy(String coSoId);

    List<SimpleEntityProj> getListChuyenNganh(String coSoId);

    List<SimpleEntityProj> getListHocKy();
}
