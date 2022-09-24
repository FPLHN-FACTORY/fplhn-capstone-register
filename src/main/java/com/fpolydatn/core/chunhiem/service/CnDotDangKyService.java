package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnDotDangKyService {

    List<SimpleEntityProj> listDotDangKy(String coSoId);
}
