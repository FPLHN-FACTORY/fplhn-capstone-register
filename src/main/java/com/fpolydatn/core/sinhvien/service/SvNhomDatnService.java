package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.sinhvien.model.request.FindSvNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;

import java.util.List;

/**
 * @author hoangnt
 */

public interface SvNhomDatnService {
    List<SvNhomDatnResponse> getNhomDatnById(String id);

    Boolean checkTruongNhom(String idNhomDatn, String idCoSo, String idUser);

    Boolean guiYeuCauPheDuyet(String idNhomDatn, String idUser);

    PageableObject<SvSinhVienNhomDatnResponse> findAllNhomDatn(final FindSvNhomDatnRequest request);
}
