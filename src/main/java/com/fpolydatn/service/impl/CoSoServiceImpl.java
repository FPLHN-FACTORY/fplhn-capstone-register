package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.coso.CreateCoSoRequest;
import com.fpolydatn.infrastructure.request.coso.UpdateCoSoRequest;
import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.service.CoSoService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }
        coSoRepository.delete(coSo.get());
        return coSo.get();
    }

    @Override
    public CoSo addNew(@Valid CreateCoSoRequest createCoSoRequest) {
        CoSo coSo = formUtils.convertToObject(CoSo.class, createCoSoRequest);
        return coSoRepository.save(coSo);
    }

    @Override
    public CoSo update(UUID id, @Valid UpdateCoSoRequest coSoRequest) {
        Optional<CoSo>searchCoSo = coSoRepository.findById(id);
        if(Objects.isNull(searchCoSo)){
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }
        CoSo coSo = formUtils.convertToObject(CoSo.class, coSoRequest);
        coSo.setId(id);
        return coSoRepository.save(coSo);
    }
    
}
