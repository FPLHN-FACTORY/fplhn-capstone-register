package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.sinhvien.model.request.AddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;

import java.util.List;

/**
 * @author hoangnt
 */

public interface SvNhomDatnService {
    List<SvDetailNhomDatnResponse> getNhomDatnById(String id);

    Boolean checkTruongNhom(String idNhomDatn, String idCoSo, String idUser);

    Boolean guiYeuCauPheDuyet(String idNhomDatn, String idUser);

    PageableObject<SvNhomDatnResponse> findAllNhomDatn(final SvFindNhomDatnRequest request);

    List<SvShowSinhVienTheoNhomResponse> showDanhSachSinhVien(String id);

    boolean addSinhVienTheoNhom(final AddSinhVienRequest req);
}
