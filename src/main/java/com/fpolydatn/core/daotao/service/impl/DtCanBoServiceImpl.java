package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.CanBoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hoangnt
 */
@Service
public class DtCanBoServiceImpl implements DtCanBoService {

    @Autowired
    @Qualifier(CanBoRepository.NAME)
    private CanBoRepository canBoRepository;

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByTenCanBo(String id, int vaiTro) {
        return canBoRepository.findAllSimpleEntityByTenCanBo(id,vaiTro);
    }
}
