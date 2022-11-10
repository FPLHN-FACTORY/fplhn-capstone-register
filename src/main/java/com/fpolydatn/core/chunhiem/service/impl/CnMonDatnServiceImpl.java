package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.response.CnMonDatnResponse;
import com.fpolydatn.core.chunhiem.repository.CnMonDatnRepository;
import com.fpolydatn.core.chunhiem.service.CnMonDatnService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.repository.CanBoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
public class CnMonDatnServiceImpl implements CnMonDatnService {

    @Autowired
    private CnMonDatnRepository cnMonDatnRepository;

    @Override
    public List<CnMonDatnResponse> getAllNhomMonDatn(String coSoId) {
        return cnMonDatnRepository.getAllNhomMonDatn(coSoId);
    }

    @Override
    public List<CnMonDatnResponse> getAllMonDatnByNhomMon(String coSoId, String nhomMonId) {
        return cnMonDatnRepository.getAllMondatnByNhomMon(coSoId, nhomMonId);
    }

    @Override
    public List<CnMonDatnResponse> getAllMonDatnByChuyenNganh(String coSoId, String idChuNhiem) {
        return cnMonDatnRepository.getAllMonDatnByChuyenNganh(coSoId, idChuNhiem);
    }
}
