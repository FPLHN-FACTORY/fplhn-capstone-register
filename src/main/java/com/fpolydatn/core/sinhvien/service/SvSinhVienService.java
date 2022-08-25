package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienTheoNhomResponse;

import java.util.List;

/**
 * @author thangdt
 */
public interface SvSinhVienService {

    List<SvSinhVienTheoNhomResponse> findByNhomId(final SvFindSinhVienRequest request);

}
