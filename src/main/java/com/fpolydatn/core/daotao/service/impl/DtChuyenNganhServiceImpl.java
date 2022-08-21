package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.ChuyenNganhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DtChuyenNganhServiceImpl implements DtChuyenNganhService {
    @Autowired
    private DtChuyenNganhRepository chuyenNganhRepository;

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByCoSo(String coSoId) {
        return chuyenNganhRepository.findAllSimpleEntityByCoSo(coSoId);
    }
}
