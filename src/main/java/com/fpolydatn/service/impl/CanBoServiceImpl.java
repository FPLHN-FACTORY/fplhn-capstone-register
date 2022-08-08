package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.infrastructure.request.UpdateCanBoRequest;
import com.fpolydatn.model.CanBo;
import com.fpolydatn.model.CoSo;
import com.fpolydatn.model.VaiTro;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.repository.VaiTroRepository;
import com.fpolydatn.service.CanBoService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    @Autowired
    private CoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public List<CanBo> getList(int offset, int size) {
        return canBoRepository.getListCanBo(offset, size);
    }

    @Override
    public CanBo addNew(CreateCanBoRequest canBoRequest) {
        if (Objects.isNull(canBoRequest)) {
            throw new RestApiException(Message.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getTenCanBo())) {
            throw new RestApiException(Message.NAME_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getTenTaiKhoan())) {
            throw new RestApiException(Message.USERNAME_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getSoDienThoai())) {
            throw new RestApiException(Message.PHONE_NUMBER_EMPTY);
        }
        if (!canBoRequest.getSoDienThoai().matches("(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}")) {
            throw new RestApiException(Message.PHONE_NUMBER_FORMAT);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getEmailFpt())) {
            throw new RestApiException(Message.EMAIL_FPT_EMPTY);
        }
        if(!canBoRequest.getEmailFpt().matches("\\w+@fpt.edu.vn")){
            throw new RestApiException(Message.EMAIL_FPT_FORMAT);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getEmailFe())) {
            throw new RestApiException(Message.EMAIL_FE_EMPTY);
        }
        if(!canBoRequest.getEmailFe().matches("\\w+@fe.edu.vn")){
            throw new RestApiException(Message.EMAIL_FE_FORMAT);
        }
        if(ObjectUtils.isEmpty(canBoRequest.getCoSoId())){
            throw new RestApiException(Message.ID_FACILITIES_EMPTY);
        }
        Optional<CoSo> coSo = coSoRepository.findById(canBoRequest.getCoSoId());
        if(!coSo.isPresent()){
            throw new RestApiException(Message.ID_FACILITIES_NOT_FOUND);
        }
        if(ObjectUtils.isEmpty(canBoRequest.getVaiTroId())){
            throw new RestApiException(Message.ID_ROLE_EMPTY);
        }
        Optional<VaiTro> vaiTro = vaiTroRepository.findById(canBoRequest.getVaiTroId());
        if(!vaiTro.isPresent()) {
            throw new RestApiException(Message.ID_ROLE_NOT_FOUND);
        }
        CanBo canBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        return canBoRepository.save(canBo);
    }

    @Override
    public String delete(UUID id) {
        Optional<CanBo> optionalCanBo = canBoRepository.findById(id);
        if(optionalCanBo.isPresent()){
            canBoRepository.deleteById(id);
            return "Xoa thanh cong can bo";
        }
        return "Can bo khong ton tai";
  }
    public CanBo findById(UUID canBoId) {
        Optional<CanBo> canBo = canBoRepository.findById(canBoId);
        if(!canBo.isPresent()){
            throw new RestApiException(Message.ID_CADRES_NOT_FOUND);
        }
        return canBo.get();
    }

    @Override
    public CanBo update(UUID id, UpdateCanBoRequest canBoRequest) {
        if (Objects.isNull(canBoRequest)) {
            throw new RestApiException(Message.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(id)) {
            throw new RestApiException(Message.ID_CADRES_EMPTY);
        }
        Optional<CanBo> canBo = canBoRepository.findById(id);
        if (!canBo.isPresent()) {
            throw new RestApiException(Message.ID_CADRES_NOT_FOUND);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getTenCanBo())) {
            throw new RestApiException(Message.NAME_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getTenTaiKhoan())) {
            throw new RestApiException(Message.USERNAME_EMPTY);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getSoDienThoai())) {
            throw new RestApiException(Message.PHONE_NUMBER_EMPTY);
        }
        if (!canBoRequest.getSoDienThoai().matches("(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}")) {
            throw new RestApiException(Message.PHONE_NUMBER_FORMAT);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getEmailFpt())) {
            throw new RestApiException(Message.EMAIL_FPT_EMPTY);
        }
        if (!canBoRequest.getEmailFpt().matches("\\w+@fpt.edu.vn")) {
            throw new RestApiException(Message.EMAIL_FPT_FORMAT);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getEmailFe())) {
            throw new RestApiException(Message.EMAIL_FE_EMPTY);
        }
        if (!canBoRequest.getEmailFe().matches("\\w+@fe.edu.vn")) {
            throw new RestApiException(Message.EMAIL_FE_FORMAT);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getCoSoId())) {
            throw new RestApiException(Message.ID_FACILITIES_EMPTY);
        }
        Optional<CoSo> coSo = coSoRepository.findById(canBoRequest.getCoSoId());
        if (!coSo.isPresent()) {
            throw new RestApiException(Message.ID_FACILITIES_NOT_FOUND);
        }
        if (ObjectUtils.isEmpty(canBoRequest.getVaiTroId())) {
            throw new RestApiException(Message.ID_ROLE_EMPTY);
        }
        Optional<VaiTro> vaiTro = vaiTroRepository.findById(canBoRequest.getVaiTroId());
        if (!vaiTro.isPresent()) {
            throw new RestApiException(Message.ID_ROLE_NOT_FOUND);
        }
        CanBo updateCanBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        updateCanBo.setCanBoId(id);
        return canBoRepository.save(updateCanBo);
    }
}
