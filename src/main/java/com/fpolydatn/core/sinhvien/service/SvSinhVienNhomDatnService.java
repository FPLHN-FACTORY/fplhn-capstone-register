package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.sinhvien.model.request.CreateSinhVienJoinNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienTheoNhomResponse;

import java.util.List;

public interface SvSinhVienNhomDatnService {
    PageableObject<SvSinhVienNhomDatnResponse> findAllNhomDatn(final FindDtNhomDatnTheoDotRequest request);

    PageableObject<DtSinhVienResponse> showDanhSachSinhVien(final FindSinhVienRequest request);

    boolean addSinhVienTheoNhom(final CreateSinhVienJoinNhomDatnRequest req);

}
