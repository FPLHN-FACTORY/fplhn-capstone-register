package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.canbo.CreateCanBoRequest;
import com.fpolydatn.infrastructure.request.canbo.UpdateCanBoRequest;
import com.fpolydatn.model.domain.CanBo;
import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.service.CanBoService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author nguyenvv4
 */
@Service
public class CanBoServiceImpl implements CanBoService {

    @Autowired
    private CanBoRepository canBoRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public List<CanBo> getList(int offset, int size) {
        return canBoRepository.getListCanBo(offset, size);
    }

    @Override
    public CanBo addNew(@Valid CreateCanBoRequest canBoRequest) {
        Optional<CoSo> coSo = coSoRepository.findById(canBoRequest.getCoSoId());
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }

        CanBo canBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        return canBoRepository.save(canBo);
    }

    public CanBo findById(UUID canBoId) {
        Optional<CanBo> canBo = canBoRepository.findById(canBoId);
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        return canBo.get();
    }

    @Override
    public CanBo update(UUID id, @Valid UpdateCanBoRequest canBoRequest) {
        Optional<CanBo> canBo = canBoRepository.findById(id);
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        Optional<CoSo> coSo = coSoRepository.findById(canBoRequest.getCoSoId());
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }

        CanBo updateCanBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        updateCanBo.setId(id);
        return canBoRepository.save(updateCanBo);
    }
}
