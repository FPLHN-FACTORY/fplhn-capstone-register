package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.repository.DtCanBoRepository;
import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.request.DtFindChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.response.DtChuyenNganhResponse;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import java.util.List;

/**
 * @author hungpv
 */
@Service
public class DtChuyenNganhServiceImpl implements DtChuyenNganhService {
    @Autowired
    private DtChuyenNganhRepository dtChuyenNganhRepository;

    private FormUtils formUtils = new FormUtils();

    @Autowired
    private DtCanBoRepository dtCanBoRepository;

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByCoSo(String id) {
        return dtChuyenNganhRepository.findAllSimpleEntityByCoSo(id);
    }

    @Override
    public ChuyenNganh update(@Valid DtUpdateChuyenNganhRequest req) {
        Optional<ChuyenNganh> chuyenNganhById = dtChuyenNganhRepository.findById(req.getId());
        ChuyenNganh chuyenNganh = formUtils.convertToObject(ChuyenNganh.class, req);
        String chuyenNganhCheck = dtChuyenNganhRepository.checkChuyenNganhByName(chuyenNganh.getCoSoId(),
                chuyenNganh.getTenChuyenNganh(), chuyenNganh.getId());
        if (!Objects.isNull(chuyenNganhCheck)) {
            throw new RestApiException(Message.CHUYEN_NGANH_ALREADY_EXIST);
        }
        if (req.getChuNhiemBoMon() != null) {
            Optional<CanBo> chuNhiemBoMonCheck = dtCanBoRepository.findById(req.getChuNhiemBoMon());
            if (!chuNhiemBoMonCheck.isPresent()) {
                throw new RestApiException(Message.CHU_NHIEM_NOT_EXISTS);
            }
        }
        ChuyenNganh chuyenNganhUpdate = chuyenNganhById.get();
        chuyenNganhUpdate.setTenChuyenNganh(req.getTenChuyenNganh());
        chuyenNganhUpdate.setChuNhiemBoMon(req.getChuNhiemBoMon());
        return dtChuyenNganhRepository.save(chuyenNganhUpdate);
    }

    @Override
    public ChuyenNganh add(@Valid DtCreateChuyenNganhRequest req) {
        ChuyenNganh chuyenNganh = formUtils.convertToObject(ChuyenNganh.class, req);
        ChuyenNganh chuyenNganhCheck = dtChuyenNganhRepository.findByTenChuyenNganh(chuyenNganh.getTenChuyenNganh(), req.getCoSoId());
        if (!Objects.isNull(chuyenNganhCheck)) {
            throw new RestApiException(Message.CHUYEN_NGANH_ALREADY_EXIST);
        }
        if (req.getChuNhiemBoMon() != null) {
            Optional<CanBo> chuNhiemBoMonCheck = dtCanBoRepository.findById(req.getChuNhiemBoMon());
            if (!chuNhiemBoMonCheck.isPresent()) {
                throw new RestApiException(Message.CHU_NHIEM_NOT_EXISTS);
            }
        }
        return dtChuyenNganhRepository.save(chuyenNganh);
    }

    @Override
    public Boolean delete(String id, String coSoId) {
        Optional<ChuyenNganh> chuyenNganhCheck = dtChuyenNganhRepository.findById(id);
        Integer countInSinhVien = dtChuyenNganhRepository.countChuyenNganhInSinhVien(id, coSoId);
        Integer countInMonDatn = dtChuyenNganhRepository.countChuyeNganhInMonDatn(id, coSoId);
        if (!chuyenNganhCheck.isPresent()) {
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }
        if (countInSinhVien != 0) {
            throw new RestApiException(Message.CHUYEN_NGANH_KHONG_DUOC_XOA);
        }
        if (countInMonDatn != 0) {
            throw new RestApiException(Message.CHUYEN_NGANH_KHONG_DUOC_XOA);
        }
        dtChuyenNganhRepository.delete(chuyenNganhCheck.get());
        return true;
    }

    @Override
    public ChuyenNganh findById(String id) {
        Optional<ChuyenNganh> chuyenNganhCheck = dtChuyenNganhRepository.findById(id);
        if (!chuyenNganhCheck.isPresent()) {
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }

        return chuyenNganhCheck.get();
    }

    @Override
    public PageableObject<DtChuyenNganhResponse> search(final DtFindChuyenNganhRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtChuyenNganhResponse> res = dtChuyenNganhRepository.findByName(req, pageable);

        return new PageableObject(res);
    }

}
