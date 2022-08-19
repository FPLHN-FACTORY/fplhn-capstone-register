package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDTSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DTSinhVienResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface DTSinhVienService {

    PageableObject<DTSinhVienResponse> findAllSinhVien(final FindDTSinhVienRequest request);

    List searchStudent(final  FindDTSinhVienRequest request);

}
