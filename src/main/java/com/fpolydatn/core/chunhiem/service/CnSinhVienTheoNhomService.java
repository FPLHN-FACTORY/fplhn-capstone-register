package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.common.base.PageableObject;

public interface CnSinhVienTheoNhomService {
    PageableObject<CnSinhVienTheoNhomResponse> searchByIdNhom(final CnFindSinhVienTheoNhomRequest req);

    boolean addSinhVienTheoNhom(String maNhom,String maSinhVien);

    long getSizeListSinhVien(String idNhom);

    boolean deleteFromIdSinhVien(String maSinhVien);

    boolean addGvhd(String giangVienId,String idNhom);
}
