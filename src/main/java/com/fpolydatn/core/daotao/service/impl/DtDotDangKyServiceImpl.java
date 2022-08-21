package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DtDotDangKyServiceImpl implements DtDotDangKyService {
    @Autowired
    private DtDotDangKyRepository dtDotDangKyRepository;
    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByCoSo(String coSoId) {
        return dtDotDangKyRepository.findAllSimpleEntity(coSoId);
    }
}
