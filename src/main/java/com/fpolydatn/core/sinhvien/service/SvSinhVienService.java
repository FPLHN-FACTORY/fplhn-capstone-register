package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienTheoNhomResponse;
import com.fpolydatn.entity.SinhVien;

import java.util.List;

/**
 * @author thangdt
 */
public interface SvSinhVienService {

    List<SvSinhVienTheoNhomResponse> findByNhomId(final SvFindSinhVienRequest request);

    String getNhomIdById(String coSoId, String dotDangKyId, String id);

    Integer getSoThanhVienByNhomId(String nhomId);

    String getMonDatnIdBySinhVienId(String id);

}
