package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.model.HocKy;
import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.service.HocKyService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    public List<HocKy> getList(int offset, int size) {
        return hocKyRepository.getListHocKy(offset, size);
    }

    @Override
    public HocKy addNew(CreateHocKyRequest hocKyRequet) {
        if (ObjectUtils.isEmpty(hocKyRequet.getTenHocKy())) {
            throw new RestApiException(Message.TEN_HOC_KY_EMPTY);
        }
        HocKy hocKy = formUtils.convertToObject(HocKy.class, hocKyRequet);
        return hocKyRepository.save(hocKy);
    }

    @Override
    public void delete(UUID id) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if(!hocKyCheck.isPresent()){
            throw new RestApiException(Message.ID_IS_NOT_PRESENT);
        }
        hocKyRepository.delete(hocKyCheck.get());
    }

    @Override
    public HocKy update(UUID id, HocKy request) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if(!hocKyCheck.isPresent()){
            throw new RestApiException(Message.ID_IS_NOT_PRESENT);
        }
        if (ObjectUtils.isEmpty(request.getTenHocKy())) {
            throw new RestApiException(Message.TEN_HOC_KY_EMPTY);
        }
        HocKy hocKy = hocKyCheck.get();
        hocKy.setTenHocKy(request.getTenHocKy());
        return hocKyRepository.save(hocKy);
    }

    @Override
    public HocKy findById(UUID id) {
        if(ObjectUtils.isEmpty(id)){
            throw new RestApiException(Message.DATA_EMPTY);
        }
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if(!hocKyCheck.isPresent()){
            throw new RestApiException(Message.ID_IS_NOT_PRESENT);
        }
        return hocKyCheck.get();
    }

    @Override
    public long getSizeListHocKy() {
        return hocKyRepository.count();
    }

}
