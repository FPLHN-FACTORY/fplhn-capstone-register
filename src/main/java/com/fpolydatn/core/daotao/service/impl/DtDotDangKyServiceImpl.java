package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class DtDotDangKyServiceImpl implements DtDotDangKyService {
    @Autowired
    private DtDotDangKyRepository dotDangKyRepository;
    @Override
    public List<SimpleEntityProj> listDotDangKy(String coSoId) {
        return dotDangKyRepository.findAllSimpleEntity(coSoId);
    }
}
