package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.service.ChuyenNganhService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hungpv
 */
@Service
public class ChuyenNganhServiceImpl implements ChuyenNganhService {
    @Autowired
    private DtChuyenNganhRepository dtChuyenNganhRepository;

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByCoSo(String id) {
        return dtChuyenNganhRepository.findAllSimpleEntityByCoSo(id);
    }
}
