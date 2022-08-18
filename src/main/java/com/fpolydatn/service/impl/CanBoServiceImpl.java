package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.command.canbo.CreateCanBoCommand;
import com.fpolydatn.infrastructure.command.canbo.UpdateCanBoCommand;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.model.entity.CanBoEntity;
import com.fpolydatn.model.entity.CoSoEntity;
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
    public List<CanBoEntity> getList(int offset, int size) {
        return canBoRepository.getListCanBo(offset, size);
    }

    @Override
    public CanBoEntity addNew(@Valid CreateCanBoCommand canBoRequest) {
        Optional<CoSoEntity> coSo = coSoRepository.findById(canBoRequest.getCoSoId());
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }

        CanBoEntity canBoEntity = formUtils.convertToObject(CanBoEntity.class, canBoRequest);
        return canBoRepository.save(canBoEntity);
    }

    public CanBoEntity findById(String canBoId) {
        Optional<CanBoEntity> canBo = canBoRepository.findById(canBoId);
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        return canBo.get();
    }

    @Override
    public CanBoEntity update(String id, @Valid UpdateCanBoCommand canBoRequest) {
        Optional<CanBoEntity> canBo = canBoRepository.findById(id);
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        Optional<CoSoEntity> coSo = coSoRepository.findById(canBoRequest.getCoSoId());
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }

        CanBoEntity updateCanBoEntity = formUtils.convertToObject(CanBoEntity.class, canBoRequest);
        updateCanBoEntity.setId(id);
        return canBoRepository.save(updateCanBoEntity);
    }

    @Override
    public CanBo findCanBoBYEmailAndCoSo(String coSoID, String email) {
        CanBo canBo = canBoRepository.findCanBoByEmailAndCoSo(coSoID, email);
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.TAI_KHOANG_KHONG_THE_DANG_NHAP_VAO_HE_THONG);
        }
        return canBo;
    }
}
