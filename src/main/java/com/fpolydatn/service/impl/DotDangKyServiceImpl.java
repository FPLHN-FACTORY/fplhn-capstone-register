package com.fpolydatn.service.impl;


import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.model.DotDangKy;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.service.DotDangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author caodinh
 */
@Service
public class DotDangKyServiceImpl implements DotDangKyService {

    @Autowired
    private DotDangKyRepository dotDangKyRepository;

    @Override
    public DotDangKy findByID(UUID id) {
        Optional<DotDangKy> dotDangKy = dotDangKyRepository.findById(id);
        if(!dotDangKy.isPresent()){
           throw new RestApiException(StatusCode.ID_DOTDANGKY_EMPTY);
        }
        return dotDangKy.get();
    }
}
