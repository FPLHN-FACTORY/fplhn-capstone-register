package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienSearchResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.SinhVien;

import java.util.List;

public interface CnSinhVienTheoNhomService {
    PageableObject<CnSinhVienTheoNhomResponse> searchByIdNhom(final CnFindSinhVienTheoNhomRequest req);

    boolean addSinhVienTheoNhom(String maNhom,String maSinhVien);

    Integer getSizeListSinhVien(String idNhom, String coSoId);

    boolean deleteFromIdSinhVien(String idNhom, String maSinhVien, String coSoId);

    boolean addGvhd(String giangVienId,String idNhom);

    boolean changeCaptain(String idCaptainOwn,String idNhom);

    boolean changeCaptainToOther(String idCaptainOwn,String idNhom);

    SinhVien getOldCaptain(String idNhom);

    List<SinhVien> getStudentNotCaptain(String idCaptain,String idGroup);

    List<DotDangKy> getListDotDangKy();

    List<CnSinhVienSearchResponse> getListSinhVienNoGroup(final CnFindSinhVienNoGroupRequest request, String idChuNhiem);

}