package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnChuyenNganhService {

    List<SimpleEntityProj> listTenChuyenNganh(String coSoId);

    List<SimpleEntityProj> getAllChuyenNganhByIdChuNhiem(String coSoId, String idChuNhiem);

    List<SimpleEntityProj> getAllMonDatnByIdChuNhiem(String coSoId, String idChuNhiem);

    List<String> getAllIdChuyenNganhByIdChuNhiem(String coSoId, String idChuNhiem);
}
