package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.admin.model.request.CreateHocKyRequest;
import com.fpolydatn.core.admin.model.request.UpdateHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.CreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.FindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.UpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.MonDatnResponse;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.service.MonDatnService;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author hungpv
 */
@Service
@Validated
public class MonDatnServiceImpl implements MonDatnService {
    @Autowired
    private DtMonDatnRepository monDatnRepository;

    private FormUtils formUtils = new FormUtils();
    @Override
    public PageableObject<MonDatnResponse> search(final FindMonDatnRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<MonDatnResponse> res = monDatnRepository.findByName(req, pageable);
        return new PageableObject(res);
    }

    @Override
    public long getSizeListMonDatn(String tenMonDatn) {
        return monDatnRepository.countByName(tenMonDatn);
    }

    @Override
    public Boolean deleteMonDatn(final String id) {
        Optional<MonDatn> monCheck = monDatnRepository.findById(id);
        if (Objects.isNull(monCheck)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        monDatnRepository.delete(monCheck.get());
        return true;
    }

    @Override
    public MonDatn findById(String id) {
        Optional<MonDatn> monCheck = monDatnRepository.findById(id);
        if (Objects.isNull(monCheck)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        return monCheck.get();
    }

    @Override
    public MonDatn update(@Valid final UpdateMonDatnRequest command) {
        Optional<MonDatn> monDatnCheck = monDatnRepository.findById(command.getId());
        if (!monDatnCheck.isPresent()) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        MonDatn monDatn = monDatnCheck.get();
        monDatn.setMaMon(command.getMaMon());
        monDatn.setTenMonDatn(command.getTenMonDatn());
        return monDatnRepository.save(monDatn);
    }

    @Override
    public MonDatn create(CreateMonDatnRequest command) {
        return null;
    }

}
