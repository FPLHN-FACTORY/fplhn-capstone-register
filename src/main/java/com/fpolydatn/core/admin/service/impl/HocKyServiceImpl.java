package com.fpolydatn.core.admin.service.impl;

import com.fpolydatn.core.admin.model.request.CreateHocKyRequest;
import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.request.UpdateHocKyRequest;
import com.fpolydatn.core.admin.repository.AdHocKyRepository;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.core.admin.service.HocKyService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author vinhnv
 */
@Service
@Validated
public class HocKyServiceImpl implements HocKyService {

    @Autowired
    private AdHocKyRepository hocKyRepository;

    private final FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<HocKyResponse> search(final FindHocKyRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<HocKyResponse> res = hocKyRepository.findByName(request, pageable);
        return new PageableObject(res);
    }

    @Override
    public HocKy create(@Valid final CreateHocKyRequest cmd) {
        HocKy hocKy = formUtils.convertToObject(HocKy.class, cmd);
        return hocKyRepository.save(hocKy);
    }

    @Override
    public Boolean delete(final String id) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        hocKyRepository.delete(hocKyCheck.get());
        return true;
    }

    @Override
    public HocKy update(@Valid final UpdateHocKyRequest cmd) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(cmd.getId());
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }

        HocKy hocKy = hocKyCheck.get();
        hocKy.setTenHocKy(cmd.getTenHocKy());

        return hocKyRepository.save(hocKy);
    }

    @Override
    public HocKy findById(String id) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        return hocKyCheck.get();
    }

    @Override
    public long getSizeListHocKy(String tenHocKy) {
        return hocKyRepository.countByName(tenHocKy);
    }

    @Override
    public List<SimpleEntityProj> getListHocKy() {
        return hocKyRepository.findAllSimpleEntity();
    }

}
