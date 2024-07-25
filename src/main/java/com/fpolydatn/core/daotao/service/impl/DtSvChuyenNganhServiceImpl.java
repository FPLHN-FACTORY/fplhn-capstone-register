package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.service.DtSvChuyenNganhService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
@Validated
public class DtSvChuyenNganhServiceImpl implements DtSvChuyenNganhService {

    @Autowired
    private DtChuyenNganhRepository chuyenNganhRepository;

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByCoSo(String coSoId) {
        return chuyenNganhRepository.findAllSimpleEntityByCoSo(coSoId);
    }

}
