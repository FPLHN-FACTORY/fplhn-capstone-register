package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.service.DtMonDatnService;
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

/**
 * @author hungpv
 */
@Service
@Validated
public class DtMonDatnServiceImpl implements DtMonDatnService {
    @Autowired
    private DtMonDatnRepository monDatnRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<DtMonDatnResponse> search(@Valid final DtFindMonDatnRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtMonDatnResponse> res = monDatnRepository.findByName(req, pageable);
        return new PageableObject(res);
    }

    @Override
    public Boolean delete(final String id) {
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
    public MonDatn update(@Valid final DtUpdateMonDatnRequest command) {
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
    public Boolean add(@Valid final DtCreateMonDatnRequest command) {
        MonDatn monDatn = formUtils.convertToObject(MonDatn.class, command);
        MonDatn monDatnInDb = monDatnRepository.findByMaMon(monDatn.getMaMon());
        if (!Objects.isNull(monDatnInDb)) {
            throw new RestApiException(Message.MON_DATN_ALREADY_EXIST);
        } else {
            monDatnRepository.save(monDatn);
            return true;
        }
    }

}
