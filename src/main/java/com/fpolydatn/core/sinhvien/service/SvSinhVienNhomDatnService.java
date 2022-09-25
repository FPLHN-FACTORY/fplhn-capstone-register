package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.sinhvien.model.request.CreateSinhVienJoinNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.FindSvNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;


public interface SvSinhVienNhomDatnService {
    PageableObject<SvSinhVienNhomDatnResponse> findAllNhomDatn(final FindSvNhomDatnRequest request);

    PageableObject<DtSinhVienResponse> showDanhSachSinhVien(final FindSinhVienRequest request);

    boolean addSinhVienTheoNhom(final CreateSinhVienJoinNhomDatnRequest req);

}
