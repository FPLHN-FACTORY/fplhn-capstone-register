package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.sinhvien.model.response.SvMonDatnResponse;

import java.util.List;

/**
 * @author SonPT
 */

public interface SvMonDatnService {

    List<SvMonDatnResponse> getMonDatn();

    String getNhomMonDatnIdByMonChuongTrinhId(String id);
    List<SvMonDatnResponse> getMonDatnTuongDuong(String id);

}
