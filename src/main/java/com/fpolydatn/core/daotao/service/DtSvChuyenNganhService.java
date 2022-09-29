package com.fpolydatn.core.daotao.service;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface DtSvChuyenNganhService {

    List<SimpleEntityProj> findAllSimpleEntityByCoSo(String coSoId);

    List<SimpleEntityProj> getListChuyenNganh(String coSoId);

}
