package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.response.CnGiangVienHuongDanResponse;
import com.fpolydatn.core.sinhvien.model.response.SvGiangVienHuongDanResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnGiangVienHuongDanService {

    List<String> getListGvhd(final CnFindGvhdRequest request);

    List<CnGiangVienHuongDanResponse> getGiangVienHuongDanByIdMonDatn(String idMon, String coSoId, String dotDangKyId);
}
