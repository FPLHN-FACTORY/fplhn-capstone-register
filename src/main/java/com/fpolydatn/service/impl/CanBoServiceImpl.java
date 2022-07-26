package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.model.CanBo;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.service.CanBoService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author nguyenvv4
 */
@Service
public class CanBoServiceImpl implements CanBoService {

    @Autowired
    private CanBoRepository canBoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public List<CanBo> getList(int offset, int size) {
        return canBoRepository.getListCanBo(offset, size);
    }

    @Override
    public CanBo addNew(CreateCanBoRequest canBoRequest) {
        if (Objects.isNull(canBoRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getTenCanBo())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getTenTaiKhoan())) {
            throw new RestApiException(StatusCode.USERNAME_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getSoDienThoai())) {
            throw new RestApiException(StatusCode.PHONE_NUMBER_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getEmailFpt())) {
            throw new RestApiException(StatusCode.EMAIL_FPT_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getEmailFe())) {
            throw new RestApiException(StatusCode.EMAIL_FE_EMPTY);
        }

        CanBo canBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        return canBoRepository.save(canBo);
    }
}
