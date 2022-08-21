package com.fpolydatn.core.admin.service.daotao.service;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author hoangnt
 */
public interface DtCanBoService {

    List<SimpleEntityProj> findAllSimpleEntityByTenCanBo(String id, int vaiTro);
}
