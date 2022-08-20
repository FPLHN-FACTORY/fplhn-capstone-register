package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.model.request.CreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.UpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.response.CanBoResponse;
import com.fpolydatn.core.daotao.model.response.TenCanBoResponse;
import com.fpolydatn.core.daotao.repository.DTCanBoRepository;
import com.fpolydatn.core.daotao.service.CanBoService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

;

/**
 * @author nguyenvv4
 */
@Service
public class CanBoServiceImpl implements CanBoService {

    @Autowired
    private DTCanBoRepository dtCanBoRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public List<CanBoResponse> findAllCanBoResponse(UUID coSoId, int offset, int size) {
        String id = String.valueOf(coSoId);
        return dtCanBoRepository.findAllCanBoResponse(id, offset, size);
    }

    @Override
    public CanBo addNew(@Valid CreateCanBoRequest canBoRequest) {
        Optional<CoSo> coSo = coSoRepository.findById(canBoRequest.getCoSoId().toString());
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }

        CanBo canBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        return dtCanBoRepository.save(canBo);
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<CanBo> canBoCheck = dtCanBoRepository.findById(id.toString());
        if (Objects.isNull(canBoCheck)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        dtCanBoRepository.delete(canBoCheck.get());
        return true;
    }

    @Override
    public CanBo findById(UUID id) {
        Optional<CanBo> canBo = dtCanBoRepository.findById(id.toString());
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        return canBo.get();
    }

    @Override
    public CanBo update(UUID id, UpdateCanBoRequest canBoRequest) {
        Optional<CanBo> canBo = dtCanBoRepository.findById(id.toString());
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        Optional<CoSo> coSo = coSoRepository.findById(canBoRequest.getCoSoId().toString());
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }
        CanBo updateCanBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        updateCanBo.setId(id.toString());
        return dtCanBoRepository.save(updateCanBo);
    }

    @Override
    public Long countCanBoByCoSoId(UUID coSoId) {
        String id = String.valueOf(coSoId);
        return dtCanBoRepository.countCanBoByCoSoID(id);
    }


    @Override
    public List<TenCanBoResponse> findCanBoByVaiTro() {
        return dtCanBoRepository.findCanBoByVaiTro();
    }

}
