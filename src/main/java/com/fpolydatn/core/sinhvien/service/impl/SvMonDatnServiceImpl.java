package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.model.response.SvMonDatnResponse;
import com.fpolydatn.core.sinhvien.repository.SvMonDatnRepository;
import com.fpolydatn.core.sinhvien.repository.SvSinhVienRepository;
import com.fpolydatn.core.sinhvien.service.SvMonDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SonPT
 */

@Service
public class SvMonDatnServiceImpl implements SvMonDatnService {

    @Autowired
    private SvMonDatnRepository svMonDatnRepository;

    @Autowired
    private SvSinhVienRepository svSinhVienRepository;

    @Override
    public List<SvMonDatnResponse> getAllMonDatnByIdUser(String id, String coSoId, String dotDangKyId) {
        String monChuongTrinhId = svSinhVienRepository.getMonChuongTrinhIdById(id, coSoId, dotDangKyId);
        String nhomMonDatnId = svMonDatnRepository.getNhomMonDatnIdById(monChuongTrinhId, coSoId);
        return svMonDatnRepository.getMonDatnByNhomMonId(nhomMonDatnId, coSoId);
    }

}
