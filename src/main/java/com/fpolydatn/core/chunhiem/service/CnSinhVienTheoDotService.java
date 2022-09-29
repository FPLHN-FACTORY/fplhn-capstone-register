package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.common.base.PageableObject;

import java.util.List;

public interface CnSinhVienTheoDotService {

    PageableObject<CnSinhVienTheoDotResponse> searchByIdDotDangKy(final CnFindSinhVienTheoDotRequest req);

    boolean addSinhVienTheoNhom(String maNhom,String maSinhVien);

    List<CnSinhVienTheoDotResponse> getSinhVienByTenSinhVien(String tenSinhVien,String coSoId);
}
