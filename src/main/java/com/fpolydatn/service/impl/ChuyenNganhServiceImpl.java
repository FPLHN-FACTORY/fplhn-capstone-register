package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.chuyennganh.CreateChuyenNganhRequest;
import com.fpolydatn.model.domain.ChuyenNganh;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.service.ChuyenNganhService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;

    private FormUtils formUtils = new FormUtils();

    public ChuyenNganh insert(CreateChuyenNganhRequest craCreateChuyenNganhRequest){
        if (Objects.isNull(craCreateChuyenNganhRequest)) {
            throw new RestApiException(Message.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(craCreateChuyenNganhRequest.getTenChuyenNganh())) {
            throw new RestApiException(Message.TEN_CHUYEN_NGANH_EMPTY);
        }
        ChuyenNganh chuyenNganh = formUtils.convertToObject(ChuyenNganh.class,craCreateChuyenNganhRequest);
        return chuyenNganhRepository.save(chuyenNganh);
    }

    @Override
    public ChuyenNganh search(UUID id) {
        Optional<ChuyenNganh> chuyenNganh = chuyenNganhRepository.findById(id);
        if(!chuyenNganh.isPresent()) {
            throw new RestApiException(Message.ID_IS_NOT_PRESENT);
        }
        return chuyenNganh.get();
    }

}
