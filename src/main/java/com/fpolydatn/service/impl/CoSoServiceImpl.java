package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.service.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fpolydatn.infrastructure.request.coso.CreateCoSoRequest;
import com.fpolydatn.infrastructure.request.coso.UpdateCoSoRequest;
import com.fpolydatn.util.FormUtils;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @author caodinh
 */
@Service
public class CoSoServiceImpl implements CoSoService {

    @Autowired
    private CoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();


    @Override
    public CoSo findById(UUID id) {
        CoSo coSo = new CoSo();
        Optional<CoSo> optionalCoSo = coSoRepository.findById(id);
        if (optionalCoSo.isPresent()) {
            coSo = optionalCoSo.get();
        }
        return coSo;
    }

    @Override
    public List<CoSo> getListCoSo(int offset, int size) {
        return coSoRepository.getListCoSo(offset, size);
    }

    @Override
    public CoSo deleteCoSo(UUID id) {
        Optional<CoSo> coSo = coSoRepository.findById(id);
        if (!coSo.isPresent()) {
            throw new RestApiException(Message.ID_COSO_NOT_EXIST);
        }
        coSoRepository.delete(coSo.get());
        return coSo.get();
    }

    @Override
    public CoSo addNew(CreateCoSoRequest createCoSoRequest) {
        if (Objects.isNull(createCoSoRequest)) {
            throw new RestApiException(Message.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(createCoSoRequest.getTenCoSo())) {
            throw new RestApiException(Message.COSO_NAME_EMPTY);
        }
        CoSo coSo = formUtils.convertToObject(CoSo.class, createCoSoRequest);
        return coSoRepository.save(coSo);
    }

    @Override
    public CoSo update(UUID uuid, UpdateCoSoRequest coSoRequest) {
        if (Objects.isNull(coSoRequest)) {
            throw new RestApiException(Message.DATA_EMPTY);
        }

        if (ObjectUtils.isEmpty(uuid)) {
            throw new RestApiException(Message.UUID_EMPTY);
        }

        if (ObjectUtils.isEmpty(coSoRequest.getTenCoSo())) {
            throw new RestApiException(Message.COSO_NAME_EMPTY);
        }

        CoSo coSo = formUtils.convertToObject(CoSo.class, coSoRequest);
        coSo.setId(uuid);
        return coSoRepository.save(coSo);
    }
    
}
