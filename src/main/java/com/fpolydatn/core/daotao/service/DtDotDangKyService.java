package com.fpolydatn.core.daotao.service;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author Vinhnv
 */
public interface DtDotDangKyService {

    List<SimpleEntityProj> findAllSimpleEntity(String coSoId );
}
