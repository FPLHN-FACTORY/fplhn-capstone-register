package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.sinhvien.model.response.SvGiangVienHuongDanResponse;


import java.util.List;

/**
 * @author ToanNT
 */
public interface SvGiangVienHuongDanService {
    List<SvGiangVienHuongDanResponse> getGvhdByMonDatnId(String maMon, String coSoId);

}
