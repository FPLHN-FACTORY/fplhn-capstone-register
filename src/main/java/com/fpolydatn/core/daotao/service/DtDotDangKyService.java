package com.fpolydatn.core.daotao.service;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

public interface DtDotDangKyService {
    List<SimpleEntityProj> findAllSimpleEntityByCoSo(String coSoId);
}
