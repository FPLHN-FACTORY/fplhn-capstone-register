package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.repository.CnDotDangKyRepository;
import com.fpolydatn.core.chunhiem.service.CnDotDangKyService;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class CnDotDangKyServiceImpl implements CnDotDangKyService {

    @Autowired
    private CnDotDangKyRepository cnDotDangKyRepository;

    @Override
    public List<SimpleEntityProj> listDotDangKy(String coSoId) {
        return cnDotDangKyRepository.findAllSimpleEntity(coSoId);
    }

    @Override
    public DotDangKy findById(String id) {
        return cnDotDangKyRepository.findById(id).get();
    }
}
