package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateVaiTroRequest;
import com.fpolydatn.model.VaiTro;
import com.fpolydatn.repository.VaiTroRepository;
import com.fpolydatn.service.VaiTroService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class VaiTroServiceImpl implements VaiTroService {

    @Autowired
    private VaiTroRepository vaiTroRepository;

    private FormUtils formUtil = new FormUtils();

    @Override
    public List<VaiTro> getList(int offset, int size) {
        return vaiTroRepository.getListVaiTro(offset, size);
    }

    @Override
    public VaiTro addNew(CreateVaiTroRequest vaiTroRequest) {
        if (Objects.isNull(vaiTroRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(vaiTroRequest.getTenVaiTro())) {
            throw new RestApiException(StatusCode.NAME_ROLE_EMPTY);
        }
        VaiTro vaiTro = formUtil.convertToObject(VaiTro.class, vaiTroRequest);
        return vaiTroRepository.save(vaiTro);
    }

    @Override
    public String delete(UUID id) {
        Optional<VaiTro> optionalVaiTro = vaiTroRepository.findById(id);
        if (optionalVaiTro.isPresent()) {
            vaiTroRepository.deleteById(id);
            return "Xoa thanh cong";
        }
        return "Vai tro khong ton tai";
    }

    @Override
    public VaiTro findByID(UUID id) {
        VaiTro vaiTro = new VaiTro();
        Optional<VaiTro> optionalVaiTro = vaiTroRepository.findById(id);
        if (optionalVaiTro.isPresent()) {
            vaiTro = optionalVaiTro.get();
        }
        return vaiTro;
    }



}
