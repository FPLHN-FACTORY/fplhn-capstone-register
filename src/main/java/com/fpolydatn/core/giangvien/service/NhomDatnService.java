package com.fpolydatn.core.giangvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.UpdateNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.NhomDatnResponse;
import com.fpolydatn.entity.NhomDatn;

import java.util.List;

/**
 * @author thangdt
 */


public interface NhomDatnService {

    PageableObject<NhomDatnResponse> fillAllNhomDatn( FindNhomDatnRequest request);

    List<String> listHocKy();

    List<String> listDotDangKi();

    List<String> listChuyenNganh();

    NhomDatn updateTrangThaiNhom( UpdateNhomDatnRequest request);
}
