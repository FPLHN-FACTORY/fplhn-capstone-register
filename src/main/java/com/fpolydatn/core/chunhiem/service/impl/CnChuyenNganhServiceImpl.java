package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.repository.CnChuyenNganhRepository;
import com.fpolydatn.core.chunhiem.service.CnChuyenNganhService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
public class CnChuyenNganhServiceImpl implements CnChuyenNganhService {

    @Autowired
    private CnChuyenNganhRepository chuyenNganhRepository;

    @Override
    public List<SimpleEntityProj> listTenChuyenNganh(String coSoId) {
        return chuyenNganhRepository.findAllSimpleEntityByCoSo(coSoId);
    }

    @Override
    public List<SimpleEntityProj> getAllChuyenNganhByIdChuNhiem(String coSoId, String idChuNhiem) {
        return chuyenNganhRepository.getAllChuyenNganhByIdChuNhiem(coSoId, idChuNhiem);
    }

    @Override
    public List<SimpleEntityProj> getAllMonDatnByIdChuNhiem(String coSoId, String idChuNhiem) {
        return chuyenNganhRepository.getAllMonDatnByIdChuNhiem(coSoId, idChuNhiem);
    }

    @Override
    public List<String> getAllIdChuyenNganhByIdChuNhiem(String coSoId, String idChuNhiem) {
        return chuyenNganhRepository.getAllIdChuyenNganhByIdChuNhiem(coSoId, idChuNhiem);
    }
}
