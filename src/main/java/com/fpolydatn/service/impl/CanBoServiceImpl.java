package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.model.CanBo;
import com.fpolydatn.model.VaiTro;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.VaiTroRepository;
import com.fpolydatn.service.CanBoService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
    private VaiTroRepository vaiTroRepository;

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
        } else if (!canBoRequest.getSoDienThoai().matches("(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}")) {
            throw new RestApiException(StatusCode.PHONE_NUMBER_FORMAT);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getEmailFpt())) {
            throw new RestApiException(StatusCode.EMAIL_FPT_EMPTY);
        }else if(!canBoRequest.getEmailFpt().matches("\\w+@fpt.edu.vn")){
            throw new RestApiException(StatusCode.EMAIL_FPT_FORMAT);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getEmailFe())) {
            throw new RestApiException(StatusCode.EMAIL_FE_EMPTY);
        }else if(!canBoRequest.getEmailFe().matches("\\w+@fe.edu.vn")){
            throw new RestApiException(StatusCode.EMAIL_FE_FORMAT);
        }
        if(ObjectUtils.isEmpty(canBoRequest.getCoSoId())){
            throw new RestApiException(StatusCode.ID_facilities_EMPTY);
        }else{

        }
        if(ObjectUtils.isEmpty(canBoRequest.getVaiTroId())){
            throw new RestApiException(StatusCode.ID_ROLE_EMPTY);
        }else {
            Optional<VaiTro> vaiTro = vaiTroRepository.findById(canBoRequest.getVaiTroId());
            if(!vaiTro.isPresent()){
                throw new RestApiException(StatusCode.ID_ROLE_NOT_FOUND);
            }
        }
        CanBo canBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        return canBoRepository.save(canBo);
    }

    @Override
    public CanBo findById(UUID canBoId) {
        CanBo canBo = canBoRepository.findById(canBoId).orElse(null);
        return canBo;
    }
}
