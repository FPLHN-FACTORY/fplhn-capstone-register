package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.FindSinhVienRequest;
import com.fpolydatn.core.chunhiem.model.response.SinhVienTheoNhomResponse;
import com.fpolydatn.core.common.base.PageableObject;

public interface SinhVienTheoNhomService {
    PageableObject<SinhVienTheoNhomResponse> searchByIdNhom(final FindSinhVienRequest req);

    boolean addSinhVienTheoNhom(String maNhom,String maSinhVien);

    long getSizeListSinhVien(String idNhom);

    boolean deleteFromIdSinhVien(String maSinhVien);

}
