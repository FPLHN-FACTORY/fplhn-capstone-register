package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.model.CoSo;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.service.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.fpolydatn.infrastructure.request.CreateCoSoRequest;
import com.fpolydatn.infrastructure.request.UpdateCoSoRequest;
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

    public List<CoSo> getListCoSo(int offset, int size) {
        return coSoRepository.getListCoSo(offset, size);
    }

    @Override
    public CoSo deleteCoSo(UUID id) {
        Optional<CoSo> coSo = coSoRepository.findById(id);
        if (!coSo.isPresent()) {
            throw new RestApiException(StatusCode.ID_COSO_NOT_EXIST);
        }
        coSoRepository.delete(coSo.get());
        return coSo.get();
    }

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
