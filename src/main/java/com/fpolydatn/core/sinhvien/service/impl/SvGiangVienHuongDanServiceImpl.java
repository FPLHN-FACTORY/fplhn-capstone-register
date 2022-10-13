package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.model.response.SvGiangVienHuongDanResponse;
import com.fpolydatn.core.sinhvien.repository.SvGiangVienHuongDanRepository;
import com.fpolydatn.core.sinhvien.service.SvGiangVienHuongDanService;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ToanNT
 */
@Service
public class SvGiangVienHuongDanServiceImpl implements SvGiangVienHuongDanService {
    @Autowired
    SvGiangVienHuongDanRepository giangVienHuongDanRepository;

    @Autowired
    CommonHelper commonHelper;

    @Override
    public List<SvGiangVienHuongDanResponse> getGvhdByMonDatnId(String maMon, String coSoId) {
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        return giangVienHuongDanRepository.getGiangVienHuongDanByIdMonDatn(maMon, coSoId, dotDangKyId);
    }

}
