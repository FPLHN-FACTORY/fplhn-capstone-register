package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface DtNhomDatnTheoDotService {
    PageableObject<DtNhomDatnTheoDotResponse> findAllNhomDatn(final FindDtNhomDatnTheoDotRequest request);

    List<DtSinhVienResponse> showDanhSachSinhVien(String idNhom, String coSoId, String dotDangKyId);

    void DTNhomDatnExport(HttpServletResponse response, String typeExcel, final FindDtNhomDatnTheoDotRequest request) throws IOException;

}

