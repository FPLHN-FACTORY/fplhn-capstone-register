package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.chunhiem.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.NhomDatnResponse;
import com.fpolydatn.core.common.base.PageableObject;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface NhomDatnService {

    PageableObject<NhomDatnResponse> search(final FindNhomDatnRequest req);

    List<String> listTenChuyenNganh();

    List<String> listTenGvhd();

}