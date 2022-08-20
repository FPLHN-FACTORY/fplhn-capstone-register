package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.reopsitory.DtDotDangKyReposiroy;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vinhnv
 */
@Service
public class DtDotDangKyServiceImpl implements DtDotDangKyService {

    @Autowired
    private DtDotDangKyReposiroy dtDotDangKyReposiroy;


    @Override
    public List<SimpleEntityProj> findAllSimpleEntity(String coSoId) {
        return dtDotDangKyReposiroy.findAllSimpleEntity(coSoId);
    }
}
