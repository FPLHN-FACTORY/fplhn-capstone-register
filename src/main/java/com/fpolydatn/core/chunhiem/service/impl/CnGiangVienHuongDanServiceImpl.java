package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindListGvhdRequest;
import com.fpolydatn.core.chunhiem.model.response.CnGiangVienHuongDanResponse;
import com.fpolydatn.core.chunhiem.model.response.CnGvhdSearchResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSimpleListGvhdResponse;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnGiangVienHuongDanService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
public class CnGiangVienHuongDanServiceImpl implements CnGiangVienHuongDanService {

    @Autowired
    private CnGiangVienHuongDanReponsitory cnGiangVienHuongDanReponsitory;

    @Autowired
    private CnNhomDatnRepository cnNhomDatnRepository;

    @Autowired
    private CnSinhVienRepository cnSinhVienRepository;

    @Override
    public List<CnGvhdSearchResponse> getListGvhd(CnFindGvhdRequest request) {
        Optional<NhomDatn> nhomDatn = cnNhomDatnRepository.findById(request.getIdNhom());
        Optional<SinhVien> truongNhom = cnSinhVienRepository.findById(nhomDatn.get().getTruongNhomId());
        request.setMonDatnId(truongNhom.get().getMonDatnId());
        return cnGiangVienHuongDanReponsitory.getListGvhd(request);
    }

    @Override
    public List<CnGiangVienHuongDanResponse> getGiangVienHuongDanByIdMonDatn(String idMon, String coSoId, String dotDangKyId) {
        return cnGiangVienHuongDanReponsitory.getGiangVienHuongDanByIdMonDatn(idMon, coSoId, dotDangKyId);
    }

    @Override
    public PageableObject<CnSimpleListGvhdResponse> getPageGvhd(final CnFindListGvhdRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        return new PageableObject<>(cnGiangVienHuongDanReponsitory.getGvhd(pageRequest, request));
    }

}
