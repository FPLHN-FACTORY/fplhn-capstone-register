package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.CreateChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.request.FindChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.request.UpdateChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.response.ChuyenNganhResponse;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
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

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByCoSo(String id) {
        return dtChuyenNganhRepository.findAllSimpleEntityByCoSo(id);
    }
    @Override
    public ChuyenNganh update(@Valid UpdateChuyenNganhRequest req) {
        Optional<ChuyenNganh> chuyenNganhCheck = dtChuyenNganhRepository.findById(req.getId());
        if (!chuyenNganhCheck.isPresent()){
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }
        ChuyenNganh chuyenNganh = chuyenNganhCheck.get();
        chuyenNganh.setTenChuyenNganh(req.getTenChuyenNganh());
        chuyenNganh.setChuNhiemBoMon(req.getChuNhiemBoMon());

        return dtChuyenNganhRepository.save(chuyenNganh);
    }

    @Override
    public ChuyenNganh add(@Valid CreateChuyenNganhRequest req) {
        ChuyenNganh chuyenNganh = formUtils.convertToObject(ChuyenNganh.class,req);

        return dtChuyenNganhRepository.save(chuyenNganh);
    }

    @Override
    public Boolean delete(String id) {
        Optional<ChuyenNganh> chuyenNganhCheck = dtChuyenNganhRepository.findById(id);
        if (!chuyenNganhCheck.isPresent()){
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }
            dtChuyenNganhRepository.delete(chuyenNganhCheck.get());

        return true;
    }

    @Override
    public ChuyenNganh findById(String id) {
        Optional<ChuyenNganh> chuyenNganhCheck = dtChuyenNganhRepository.findById(id);
        if (!chuyenNganhCheck.isPresent()){
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }

        return chuyenNganhCheck.get();
    }

    @Override
    public PageableObject<ChuyenNganhResponse> search(final FindChuyenNganhRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<ChuyenNganhResponse> res = dtChuyenNganhRepository.findByName(req,pageable);

        return new PageableObject(res);
    }
}
