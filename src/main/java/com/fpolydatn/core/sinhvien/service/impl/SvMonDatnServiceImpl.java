package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.model.response.SvMonDatnResponse;
import com.fpolydatn.core.sinhvien.repository.SvMonDatnRepository;
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

    @Override
    public List<SvMonDatnResponse> getMonDatn() {
        return svMonDatnRepository.findMonDatn();
    }

    @Override
    public String getNhomMonDatnIdByMonChuongTrinhId(String id) {
        return svMonDatnRepository.getNhomMonDatnIdByMonChuongTrinhId(id);
    }

    @Override
    public List<SvMonDatnResponse> getMonDatnTuongDuong(String id) {
        return svMonDatnRepository.getMonDatnTuongDuong(id);
    }

}
