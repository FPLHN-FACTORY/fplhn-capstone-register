package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.sinhvien.model.request.FindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.NhomSinhVienResponse;

import java.util.List;

/**
 * @author thangdt
 */
public interface SinhVienService {

    List<NhomSinhVienResponse> findByNhomId( FindSinhVienRequest request);

}
