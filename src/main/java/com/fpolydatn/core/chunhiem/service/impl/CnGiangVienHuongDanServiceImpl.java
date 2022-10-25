package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.response.CnGiangVienHuongDanResponse;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.service.CnGiangVienHuongDanService;
import com.fpolydatn.core.sinhvien.model.response.SvGiangVienHuongDanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
public class CnGiangVienHuongDanServiceImpl implements CnGiangVienHuongDanService {

    @Autowired
    private CnGiangVienHuongDanReponsitory cnGiangVienHuongDanReponsitory;

    @Override
    public List<String> getListGvhd(CnFindGvhdRequest request) {
        return cnGiangVienHuongDanReponsitory.getListGvhd(request);
    }

    @Override
    public List<CnGiangVienHuongDanResponse> getGiangVienHuongDanByIdMonDatn(String idMon, String coSoId, String dotDangKyId) {
        return cnGiangVienHuongDanReponsitory.getGiangVienHuongDanByIdMonDatn(idMon, coSoId, dotDangKyId);
    }
}
