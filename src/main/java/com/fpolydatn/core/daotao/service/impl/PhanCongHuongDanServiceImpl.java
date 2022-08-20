package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanGiaoVienResponse;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.repository.DtPhanCongHuongDanRepository;
import com.fpolydatn.core.daotao.service.PhanCongHuongDanService;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author huynq
 */
@Service
public class PhanCongHuongDanServiceImpl implements PhanCongHuongDanService {

    @Autowired
    private DtPhanCongHuongDanRepository phanCongHuongDanRepository;

    @Override
    public PageableObject<PhanCongHuongDanResponse> search(String id, final FindPhanCongHuongDanRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<PhanCongHuongDanResponse> res = phanCongHuongDanRepository.findByName(id, req, pageable);
        return new PageableObject(res);
    }

    @Override
    public PhanCongHuongDanMonDatnResponse getMon(String id) {
        return phanCongHuongDanRepository.getMon(id);
    }

    @Override
    public PhanCongHuongDan findID(String id) {
        PhanCongHuongDan phanCongHuongDan = phanCongHuongDanRepository.finId(id);
        if(phanCongHuongDan == null) {
            throw new RestApiException(Message.PHAN_CONG_HUONG_DAN_NOT_EXIST);
        }
        return phanCongHuongDan;
    }

    @Override
    public Boolean delete(String id) {
        PhanCongHuongDan phanCongHuongDan = phanCongHuongDanRepository.finId(id);
        if (Objects.isNull(phanCongHuongDan)) {
            throw new RestApiException(Message.PHAN_CONG_HUONG_DAN_NOT_EXIST);
        }
        phanCongHuongDanRepository.delete(phanCongHuongDan);
        return true;
    }

    @Override
    public List<PhanCongHuongDanGiaoVienResponse> getPhanCongGiaoVien(String coSoId, String maMonId) {
        return phanCongHuongDanRepository.getPhanCongGiaoVien(coSoId, maMonId);
    }

    @Override
    public PhanCongHuongDan addMon(PhanCongHuongDan phanCongHuongDan) {
        return phanCongHuongDanRepository.save(phanCongHuongDan);
    }

}
