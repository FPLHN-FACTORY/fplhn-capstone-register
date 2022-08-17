package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.query.hocky.HocKyQuery;
import com.fpolydatn.infrastructure.command.hocky.CreateHocKyCommand;
import com.fpolydatn.infrastructure.command.hocky.UpdateHocKyCommand;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.model.entity.HocKyEntity;
import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.service.HocKyService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public HocKyEntity create(@Valid final CreateHocKyCommand hocKyRequest) {
        HocKyEntity hocKyEntity = formUtils.convertToObject(HocKyEntity.class, hocKyRequest);
        return hocKyRepository.save(hocKyEntity);
    }

    @Override
    public Boolean delete(final UUID id) {
        Optional<HocKyEntity> hocKyCheck = hocKyRepository.findById(id);
        if (hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        hocKyRepository.delete(hocKyCheck.get());
        return true;
    }

    @Override
    public HocKyEntity update(UpdateHocKyCommand request) {
        Optional<HocKyEntity> hocKyCheck = hocKyRepository.findById(request.getId());
        if (hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        HocKyEntity hocKyEntity = hocKyCheck.get();
        hocKyEntity.setTenHocKy(request.getTenHocKy());
        return hocKyRepository.save(hocKyEntity);
    }

    @Override
    public HocKyEntity findById(UUID id) {
        HocKyEntity hocKyCheck = hocKyRepository.findByIdHIHI(id.toString());
        if (Objects.isNull(hocKyCheck)) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        return hocKyCheck;
    }

    @Override
    public long getSizeListHocKy(String tenHocKy) {
        return hocKyRepository.countByName(tenHocKy);
    }

}
