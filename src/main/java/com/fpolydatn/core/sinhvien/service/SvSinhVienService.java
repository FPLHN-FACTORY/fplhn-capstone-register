package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomSinhVienResponse;

import java.util.List;

/**
 * @author thangdt
 */
public interface SvSinhVienService {

    List<SvNhomSinhVienResponse> findByNhomId(final SvFindSinhVienRequest request);

}
