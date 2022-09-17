package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.repository.DtHocKyRepository;
import com.fpolydatn.core.daotao.service.DtHocKyService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SonPT
 */

@Service
public class DtHocKyServiceImpl implements DtHocKyService {

    @Autowired
    private DtHocKyRepository hocKyRepository;

    @Override
    public List<SimpleEntityProj> findAllSimpleEntiry() {
        return hocKyRepository.findAllSimpleEntity();
    }

}
