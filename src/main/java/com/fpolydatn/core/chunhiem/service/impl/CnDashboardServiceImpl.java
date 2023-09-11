package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.CnFindDashboardRequest;
import com.fpolydatn.core.chunhiem.model.response.CnDashboardGvhdResponse;
import com.fpolydatn.core.chunhiem.model.response.CnDashboardNhomDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnDashboardResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSimpleDashboardGvhdResponse;
import com.fpolydatn.core.chunhiem.repository.CnDashboardRepository;
import com.fpolydatn.core.chunhiem.service.CnDashboardService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thangncph26123
 */
@Service
public class CnDashboardServiceImpl implements CnDashboardService {

    @Autowired
    private CnDashboardRepository cnDashboardRepository;

    @Override
    public CnDashboardResponse dashboard(final CnFindDashboardRequest request) {
        CnDashboardResponse cnDashboardResponse = new CnDashboardResponse();
        String tenChuyenNganh = cnDashboardRepository.getTenChuyenNganhByIdChuNhiem(request.getIdChuNhiem(), request.getCoSoId());
        cnDashboardResponse.setTenChuyenNganh(tenChuyenNganh);
        Integer tongSLSinhVien = cnDashboardRepository.getTongSoSinhVien(request);
        cnDashboardResponse.setTongSLSinhVien(tongSLSinhVien);
        Integer tongSoNhom = cnDashboardRepository.getTongSoNhom(request);
        cnDashboardResponse.setTongSoNhom(tongSoNhom);
        Integer tongSoNhomDaChotDeTai = cnDashboardRepository.getTongSoNhomDaChotDeTai(request);
        cnDashboardResponse.setTongSoNhomDaChotDeTai(tongSoNhomDaChotDeTai);
        cnDashboardResponse.setTongSoNhomChuaChotDeTai(tongSoNhom - tongSoNhomDaChotDeTai);
        Integer tongSoGvhd = cnDashboardRepository.tongSoGvhd(request);
        cnDashboardResponse.setTongSoGvhd(tongSoGvhd);
        Integer tongSoGvhdDangCoNhom = cnDashboardRepository.tongSoGvhdDangCoNhom(request);
        cnDashboardResponse.setTongSoGvhdDangCoNhom(tongSoGvhdDangCoNhom);
        Integer tongSoGvhdChuaCoNhom = tongSoGvhd - tongSoGvhdDangCoNhom;
        cnDashboardResponse.setTongSoGvhdChuaCoNhomNao(tongSoGvhdChuaCoNhom);

        List<CnDashboardGvhdResponse> gvhdResponseList = new ArrayList<>();
        List<CnSimpleDashboardGvhdResponse> listGvhd = cnDashboardRepository.getGvhd(request);
        for (CnSimpleDashboardGvhdResponse xx : listGvhd) {
            CnDashboardGvhdResponse cnDashboardGvhdResponse = new CnDashboardGvhdResponse();
            cnDashboardGvhdResponse.setGvhd(xx.getName());
            List<CnDashboardNhomDatnResponse> listNhomDatn = cnDashboardRepository.getNhomDatnByIdGiangVien(xx.getId(), request.getDotDangKyId(), request.getCoSoId());
            cnDashboardGvhdResponse.setListNhomDatn(listNhomDatn);
            cnDashboardGvhdResponse.setSoNhomDangHuongDan(listNhomDatn.size() + "/" + xx.getSoNhomHuongDanToiDa());
            gvhdResponseList.add(cnDashboardGvhdResponse);
        }
        cnDashboardResponse.setListGvhd(gvhdResponseList);
        return cnDashboardResponse;
    }

    @Override
    public List<SimpleEntityProj> getAllChuyenNganhByIdChuNhiem(final CnFindDashboardRequest request) {
        return cnDashboardRepository.getAllChuyenNganhByIdChuNhiem(request);
    }
}
