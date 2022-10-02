package com.fpolydatn.core.daotao.service;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface DtSvDotDangKyService {

    List<SimpleEntityProj> listDotDangKy(String coSoId);

    List<SimpleEntityProj> getListDotDangKy(String coSoId);

}
