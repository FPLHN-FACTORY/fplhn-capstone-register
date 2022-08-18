package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.command.coso.CreateCoSoCommand;
import com.fpolydatn.infrastructure.command.coso.UpdateCoSoCommand;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.model.entity.CoSoEntity;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.service.CoSoService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author caodinh
 */
@Service
public class CoSoServiceImpl implements CoSoService {

    @Autowired
    private CoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();


    @Override
    public CoSoEntity findById(String id) {
        CoSoEntity coSoEntity = new CoSoEntity();
        Optional<CoSoEntity> optionalCoSo = coSoRepository.findById(id);
        if (optionalCoSo.isPresent()) {
            coSoEntity = optionalCoSo.get();
        }
        return coSoEntity;
    }

    @Override
    public List<CoSoEntity> getListCoSo() {
        return coSoRepository.findAll();
    }

    @Override
    public CoSoEntity deleteCoSo(String id) {
        Optional<CoSoEntity> coSo = coSoRepository.findById(id);
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }
        coSoRepository.delete(coSo.get());
        return coSo.get();
    }

    @Override
    public CoSoEntity addNew(@Valid CreateCoSoCommand createCoSoRequest) {
        CoSoEntity coSoEntity = formUtils.convertToObject(CoSoEntity.class, createCoSoRequest);
        return coSoRepository.save(coSoEntity);
    }

    @Override
    public CoSoEntity update(String id, @Valid UpdateCoSoCommand coSoRequest) {
        Optional<CoSoEntity>searchCoSo = coSoRepository.findById(id);
        if(Objects.isNull(searchCoSo)){
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }
        CoSoEntity coSoEntity = formUtils.convertToObject(CoSoEntity.class, coSoRequest);
        coSoEntity.setId(id);
        return coSoRepository.save(coSoEntity);
    }
    
}
