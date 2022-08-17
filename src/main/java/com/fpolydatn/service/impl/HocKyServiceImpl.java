package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.command.hocky.CreateHocKyCommand;
import com.fpolydatn.infrastructure.command.hocky.UpdateHocKyCommand;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.query.hocky.HocKyQuery;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.model.domain.HocKyImpl;
import com.fpolydatn.model.entity.HocKyEntity;
import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.service.HocKyService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.swing.text.html.Option;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author vinhnv
 */
@Service
@Validated
public final class HocKyServiceImpl implements HocKyService {

    @Autowired
    private HocKyRepository hocKyRepository;

    private FormUtils formUtils = new FormUtils();


    @Override
    public List<HocKy> search(final HocKyQuery request) {
        return hocKyRepository.findByName(request);
    }

    @Override
    public HocKyEntity create(@Valid final CreateHocKyCommand cmd) {
        HocKyEntity hocKyEntity = formUtils.convertToObject(HocKyEntity.class, cmd);
        return hocKyRepository.save(hocKyEntity);
    }

    @Override
    public Boolean delete(final String id) {
        Optional<HocKyEntity> hocKyCheck = hocKyRepository.findById(id);
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        hocKyRepository.delete(hocKyCheck.get());
        return true;
    }

    @Override
    public HocKy update(@Valid final UpdateHocKyCommand cmd) {
        Optional<HocKyEntity> hocKyCheck = hocKyRepository.findById(cmd.getId());
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        HocKyEntity hocKyEntity = hocKyCheck.get();
        hocKyEntity.setTenHocKy(cmd.getTenHocKy());

        hocKyEntity = hocKyRepository.save(hocKyEntity);
        return formUtils.convertToObject(HocKyImpl.class, hocKyEntity);
    }

    @Override
    public HocKyEntity findById(String id) {
        Optional<HocKyEntity> hocKyCheck = hocKyRepository.findById(id);
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        return hocKyCheck.get();
    }

    @Override
    public long getSizeListHocKy(String tenHocKy) {
        return hocKyRepository.countByName(tenHocKy);
    }

}
