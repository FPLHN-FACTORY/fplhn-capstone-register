package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.sinhvien.model.request.SvAddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvCreateNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenDeTaiRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.entity.NhomDatn;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hoangnt
 */

public interface SvNhomDatnService {
    List<SvDetailNhomDatnResponse> getDetailNhomDatnById(String id, String coSoId, String dotDangKyId);

    Boolean checkTruongNhom(String idNhomDatn, String idCoSo, String idUser, String dotDangKyId);

    PageableObject<SvNhomDatnResponse> findAllNhomDatn(final SvFindNhomDatnRequest request, String idUser);

    List<SvShowSinhVienTheoNhomResponse> showDanhSachSinhVien(String id, String coSoId, String dotDangKyId);

    boolean addSinhVienTheoNhom(final SvAddSinhVienRequest req);

    NhomDatn createNhomDatn(@Valid SvCreateNhomDatnRequest svNhomDatnRequest, String idUser);

    Boolean updateTenDeTai(@Valid SvUpdateTenDeTaiRequest svUpdateTenDeTaiRequest);

    Boolean roiNhom(String idUser, String coSoId, String dotDangKyId);

    Boolean chuyenQuyenTruongNhom(String idUser, String idTruongNhomMoi);

    Boolean chonGvhd(String idUser, String idGiangVien, String coSoId, String dotDangKyId);

    Boolean checkThoiGianRoiNhom(String idUser);

}
