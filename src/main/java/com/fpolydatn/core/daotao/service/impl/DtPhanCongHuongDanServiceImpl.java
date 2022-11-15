package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.request.DtFindPhanMonGiangVienHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanMonDatnChoGiangVienHuongDanResponse;
import com.fpolydatn.core.daotao.repository.DtPhanCongHuongDanRepository;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.entity.PhanCongHuongDanId;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author huynq
 */
@Service
public class DtPhanCongHuongDanServiceImpl implements DtPhanCongHuongDanService {

    @Autowired
    private DtPhanCongHuongDanRepository phanCongHuongDanRepository;

    @Override
    public PageableObject<DtPhanCongHuongDanResponse> search(String id, final DtFindPhanCongHuongDanRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtPhanCongHuongDanResponse> res = phanCongHuongDanRepository.getGiangVienHuongDan(id, req, pageable);
        return new PageableObject(res);
    }

    @Override
    public DtPhanCongHuongDanMonDatnResponse getMon(String id) {
        return phanCongHuongDanRepository.getMon(id);
    }

    @Override
    public PhanCongHuongDan findId(String idGV, String idMon) {
        PhanCongHuongDanId phanCongHuongDanId = new PhanCongHuongDanId();
        phanCongHuongDanId.setMonDatnId(idMon);
        phanCongHuongDanId.setGiangVienId(idGV);
        Optional<PhanCongHuongDan> phanCongHuongDan = phanCongHuongDanRepository.findById(phanCongHuongDanId);
        if (phanCongHuongDan == null) {
            throw new RestApiException(Message.PHAN_CONG_HUONG_DAN_NOT_EXIST);
        }
        return phanCongHuongDan.get();
    }

    @Override
    public Boolean delete(String idGV, String idMon) {
        PhanCongHuongDan phanCongHuongDan = findId(idGV, idMon);
        if (Objects.isNull(phanCongHuongDan)) {
            throw new RestApiException(Message.PHAN_CONG_HUONG_DAN_NOT_EXIST);
        }
        phanCongHuongDanRepository.delete(phanCongHuongDan);
        return true;
    }

    @Override
    public List<DtPhanCongHuongDanResponse> getPhanCongGiaoVien(String coSoId, String maMonId) {
        return phanCongHuongDanRepository.getPhanCongGiaoVien(coSoId, maMonId);
    }

    @Override
    public List<PhanCongHuongDan> addAll(List<PhanCongHuongDan> list) {
        return phanCongHuongDanRepository.saveAll(list);
    }

    @Override
    public PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> getPhanMonDatn(String id, DtFindPhanMonGiangVienHuongDanRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtPhanMonDatnChoGiangVienHuongDanResponse> res = phanCongHuongDanRepository.getMonDatn(id, req, pageable);
        return new PageableObject(res);
    }

    @Override
    public DtGiangVienHDResponse getGiangVienHuongDan(String id, String coSoId) {
        return phanCongHuongDanRepository.getGiangVienHD(id, coSoId);
    }

    @Override
    public List<DtPhanMonDatnChoGiangVienHuongDanResponse> getPhanMonDatnList(String coSoId, String giangVienHuongDanId) {
        return phanCongHuongDanRepository.getPhanCongMonDatn(coSoId, giangVienHuongDanId);
    }

}
