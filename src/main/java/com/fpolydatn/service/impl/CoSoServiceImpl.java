package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateCoSoRequest;
import com.fpolydatn.infrastructure.request.UpdateCoSoRequest;
import com.fpolydatn.model.CoSo;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.service.CoSoService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.UUID;

/**
 * @author caodinh
 */
@Service
public class CoSoServiceImpl implements CoSoService {

    @Autowired
    private CoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public CoSo addNew(CreateCoSoRequest createCoSoRequest) {
        if (Objects.isNull(createCoSoRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(createCoSoRequest.getTenCoSo())) {
            throw new RestApiException(StatusCode.COSO_NAME_EMPTY);
        }
        CoSo coSo = formUtils.convertToObject(CoSo.class, createCoSoRequest);
        return coSoRepository.save(coSo);
    }

    @Override
    public CoSo update(UUID uuid, UpdateCoSoRequest coSoRequest) {
        if (Objects.isNull(coSoRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }

        if (ObjectUtils.isEmpty(uuid)) {
            throw new RestApiException(StatusCode.UUID_EMPTY);
        }

        if (ObjectUtils.isEmpty(coSoRequest.getTenCoSo())) {
            throw new RestApiException(StatusCode.COSO_NAME_EMPTY);
        }

        CoSo coSo = formUtils.convertToObject(CoSo.class, coSoRequest);
        coSo.setCoSoId(uuid);
        return coSoRepository.save(coSo);
    }
}
