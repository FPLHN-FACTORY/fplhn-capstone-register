package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.model.response.DtTenCanBoResponse;
import com.fpolydatn.core.daotao.repository.DtCanBoRepository;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
@Validated
public class DtCanBoServiceImpl implements DtCanBoService {

    @Autowired
    private DtCanBoRepository dtCanBoRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<DtCanBoResponse> search(final DtFindCanBoRequest canBoRequest) {
        Pageable pageable = PageRequest.of(canBoRequest.getPage(), canBoRequest.getSize());
        Page<DtCanBoResponse> res = dtCanBoRepository.findAllByName(canBoRequest, pageable);
        return new PageableObject(res);
    }

    @Override
    public CanBo create(@Valid final DtCreateCanBoRequest canBoRequest) {
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
    public CanBo update(UUID id, DtUpdateCanBoRequest canBoRequest) {
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

//    @Override
//    public Long countCanBoByCoSoId(String tenCanBo) {
//        return dtCanBoRepository.countByName(tenCanBo);
//    }


    @Override
    public List<DtTenCanBoResponse> findCanBoByVaiTro() {
        return dtCanBoRepository.findCanBoByVaiTro();
    }

}
