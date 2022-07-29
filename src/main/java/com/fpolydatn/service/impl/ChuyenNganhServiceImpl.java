package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateChuyenNganhRequest;
import com.fpolydatn.model.ChuyenNganh;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;

    public ChuyenNganh insert(CreateChuyenNganhRequest craCreateChuyenNganhRequest){
        if (Objects.isNull(craCreateChuyenNganhRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(craCreateChuyenNganhRequest.getTenChuyenNganh())) {
            throw new RestApiException(StatusCode.TEN_CHUYEN_NGANH_EMPTY);
        }
//        Optional<>
        return null;
    }
}
