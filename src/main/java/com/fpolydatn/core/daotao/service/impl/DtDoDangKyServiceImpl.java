package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.reopsitory.DtDotDangKyreposiroy;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vinhnv
 */
@Service
public class DtDoDangKyServiceImpl implements DtDotDangKyService {

    @Autowired
    private DtDotDangKyreposiroy dtDotDangKyreposiroy;


    @Override
    public List<SimpleEntityProj> findAllSimpleEntity(String coSoId) {
        return dtDotDangKyreposiroy.findAllSimpleEntity(coSoId);
    }
}
