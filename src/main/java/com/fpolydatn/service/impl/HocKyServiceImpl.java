package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.HocKyRequest;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.service.HocKyService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import java.util.List;
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
    public HocKy addNew(@Valid HocKyRequest hocKyRequest) {
        HocKy hocKy = formUtils.convertToObject(HocKy.class, hocKyRequest);
        return hocKyRepository.save(hocKy);
    }

    @Override
    public Boolean delete(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new RestApiException(Message.UUID_EMPTY);
        }
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.ID_IS_NOT_PRESENT);
        }
        hocKyRepository.delete(hocKyCheck.get());
        return true;
    }

    @Override
    public HocKy update(UUID id, HocKyRequest request) {
        if (ObjectUtils.isEmpty(request.getTenHocKy())) {
            throw new RestApiException(Message.TEN_HOC_KY_EMPTY);
        }
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.ID_IS_NOT_PRESENT);
        }
        HocKy hocKy = hocKyCheck.get();
        hocKy.setTenHocKy(request.getTenHocKy());
        return hocKyRepository.save(hocKy);
    }

    @Override
    public HocKy findById(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new RestApiException(Message.DATA_EMPTY);
        }
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.ID_IS_NOT_PRESENT);
        }
        return hocKyCheck.get();
    }

    @Override
    public long getSizeListHocKy(String tenHocKy) {
        return hocKyRepository.countListHocKy(tenHocKy);
    }

    @Override
    public List<HocKy> searchHocKy(String tenHocKy, int offset, int size) {
        return hocKyRepository.searchHocKy(tenHocKy, offset, size);
    }

}
