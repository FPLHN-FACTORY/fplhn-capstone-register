package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.response.CnGiangVienHuongDanResponse;
import com.fpolydatn.core.chunhiem.model.response.CnGvhdSearchResponse;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnGiangVienHuongDanService {

    List<CnGvhdSearchResponse> getListGvhd(final CnFindGvhdRequest request);

    List<CnGiangVienHuongDanResponse> getGiangVienHuongDanByIdMonDatn(String idMon, String coSoId, String dotDangKyId);
}
