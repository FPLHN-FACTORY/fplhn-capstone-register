package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;

import java.util.List;

/**
 * @author hoangnt
 */

public interface SvNhomDatnService {
    List<SvNhomDatnResponse> getNhomDatnById(String id);

    Boolean checkTruongNhom(String idNhomDatn, String idCoSo, String idUser);

    Boolean guiYeuCauPheDuyet(String idNhomDatn, String idUser);
}
