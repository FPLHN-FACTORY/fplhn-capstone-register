package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import java.util.List;

public interface DtNhomDatnTheoDotService {
    PageableObject<DtNhomDatnTheoDotResponse> findAllNhomDatn(final FindDtNhomDatnTheoDotRequest request);

    PageableObject<DtSinhVienResponse> showDanhSachSinhVien(final FindSinhVienRequest request);

    List<DtNhomDatnTheoDotResponse> listAllNhomDatnToExcel();
}

