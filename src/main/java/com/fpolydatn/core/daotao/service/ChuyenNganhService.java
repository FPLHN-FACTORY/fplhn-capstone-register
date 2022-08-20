package com.fpolydatn.core.daotao.service;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author hungpv
 */
public interface ChuyenNganhService {

    List<SimpleEntityProj> findAllSimpleEntityByCoSo(String id);
}
