package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanGiaoVienResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.repository.DtPhanCongHuongDanRepository;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
        Page<DtPhanCongHuongDanResponse> res = phanCongHuongDanRepository.findByName(id, req, pageable);
        return new PageableObject(res);
    }

    @Override
    public DtPhanCongHuongDanMonDatnResponse getMon(String id) {
        return phanCongHuongDanRepository.getMon(id);
    }

    @Override
    public PhanCongHuongDan findId(String id) {
        PhanCongHuongDan phanCongHuongDan = phanCongHuongDanRepository.finId(id);
        if (phanCongHuongDan == null) {
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
    public List<DtPhanCongHuongDanGiaoVienResponse> getPhanCongGiaoVien(String coSoId, String maMonId) {
        return phanCongHuongDanRepository.getPhanCongGiaoVien(coSoId, maMonId);
    }

    @Override
    public PhanCongHuongDan add(@Valid PhanCongHuongDan phanCongHuongDan) {
        return phanCongHuongDanRepository.save(phanCongHuongDan);
    }

}
