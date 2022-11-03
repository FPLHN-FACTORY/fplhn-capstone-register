package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.core.daotao.repository.DtNhomMonDatnRepository;
import com.fpolydatn.core.daotao.service.DtNhomMonDatnService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DtNhomMonDatnServiceImpl implements DtNhomMonDatnService {
    @Autowired
    private DtNhomMonDatnRepository dtNhomMonDatnRepository;
    private FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<DtNhomMonDatnResponse> search(DtFindNhomMonDatnRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtNhomMonDatnResponse> res = dtNhomMonDatnRepository.findByName(req, pageable);
        return new PageableObject(res);
    }

    @Override
    public Boolean delete(String id) {
        Optional<MonDatn> nhomDatnList = dtNhomMonDatnRepository.findById(id);
        dtNhomMonDatnRepository.delete(nhomDatnList.get());
        return true;
    }

    @Override
    public Boolean update(DtUpdateNhomMonDatnRequest command) {
        Optional<MonDatn> update = dtNhomMonDatnRepository.findById(command.getId());
        MonDatn monDatn = update.get();
        monDatn.setTenMonDatn(command.getTenNhomMon());
        dtNhomMonDatnRepository.save(monDatn);
        return true;
    }

    @Override
    public Boolean add(DtCreateNhomMonDatnRequest command) {
        MonDatn monDatn = formUtils.convertToObject(MonDatn.class, command);
        if (!Objects.isNull(dtNhomMonDatnRepository.findByMaMonSave(monDatn.getMaMon()))) {
            throw new RestApiException(Message.MON_DATN_ALREADY_EXIST);
        } else {
            dtNhomMonDatnRepository.save(monDatn);
            return true;
        }
    }

    @Override
    public MonDatn findById(String id) {
        Optional<MonDatn> monDatn = dtNhomMonDatnRepository.findById(id);
        if (Objects.isNull(monDatn)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        return monDatn.get();
    }
}
