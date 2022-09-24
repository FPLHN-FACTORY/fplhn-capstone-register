package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.response.CnGvhdSearchResponse;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnGiangVienHuongDanService {

    List<String> getListGvhd(final CnFindGvhdRequest request);
}
