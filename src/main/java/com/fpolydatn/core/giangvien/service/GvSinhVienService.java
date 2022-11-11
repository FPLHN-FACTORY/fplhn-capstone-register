package com.fpolydatn.core.giangvien.service;

import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienTheoNhomResponse;

import java.util.List;

/**
 * @author SonPT
 */
public interface GvSinhVienService {

    public List<SvSinhVienTheoNhomResponse> findByNhomId(final SvFindSinhVienRequest request);

}
