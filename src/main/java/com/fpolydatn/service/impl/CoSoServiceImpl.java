package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.model.CoSo;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.service.CanBoService;
import com.fpolydatn.service.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author thepvph20110
 */

@Service
public class CoSoServiceImpl implements CoSoService {

    @Autowired
    private CoSoRepository coSoRepository;

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

}
