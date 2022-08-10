package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.hocky.CreateHocKyRequest;
import com.fpolydatn.infrastructure.request.hocky.UpdateHocKyRequest;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.service.HocKyService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author vinhnv
 */
@Service
public class HocKyServiceImpl implements HocKyService {

    @Autowired
    private HocKyRepository hocKyRepository;

    private FormUtils formUtils = new FormUtils();


    @Override
    public List<HocKy> searchHocKy(String tenHocKy, int offset, int size) {
        return hocKyRepository.searchHocKy(tenHocKy, offset, size);
    }

    @Override
    public HocKy addNew(@Valid CreateHocKyRequest hocKyRequest) {
        HocKy hocKy = formUtils.convertToObject(HocKy.class, hocKyRequest);
        return hocKyRepository.save(hocKy);
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (Objects.isNull(hocKyCheck)) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        hocKyRepository.delete(hocKyCheck.get());
        return true;
    }

    @Override
    public HocKy update(UUID id, UpdateHocKyRequest request) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (Objects.isNull(hocKyCheck)) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        HocKy hocKy = hocKyCheck.get();
        hocKy.setTenHocKy(request.getTenHocKy());
        return hocKyRepository.save(hocKy);
    }

    @Override
    public HocKy findById(UUID id) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (Objects.isNull(hocKyCheck)) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        return hocKyCheck.get();
    }

    @Override
    public long getSizeListHocKy(String tenHocKy) {
        return hocKyRepository.countListHocKy(tenHocKy);
    }

}
