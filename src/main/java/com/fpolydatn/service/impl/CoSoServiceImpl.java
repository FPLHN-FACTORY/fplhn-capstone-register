package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateCoSoRequest;
import com.fpolydatn.model.CoSo;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.service.CoSoService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CoSoServiceImpl implements CoSoService {
    @Autowired
    private CoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public CoSo findById(UUID id) {
        CoSo coSo = new CoSo();
        Optional<CoSo> optionalCoSo = coSoRepository.findById(id);
         if(optionalCoSo.isPresent()){
             coSo = optionalCoSo.get();
         }
         return coSo;
    }
    @Override
    public List<CoSo> getList(int offset, int size) {
        return coSoRepository.getListCoSo(offset, size);
    }

    @Override
    public CoSo addNew(CreateCoSoRequest coSoRequest) {
        if (Objects.isNull(coSoRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(coSoRequest.getTenCoSo())) {
            throw new RestApiException(StatusCode.TEN_CO_SO_EMPTY);
        }
        CoSo coSo = formUtils.convertToObject(CoSo.class,coSoRequest);
        return coSoRepository.save(coSo);
    }
}
