package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.command.chuyennganh.CreateChuyenNganhCommand;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.model.domain.ChuyenNganh;
import com.fpolydatn.model.entity.ChuyenNganhEntity;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.service.ChuyenNganhService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;

    private FormUtils formUtils = new FormUtils();

    public ChuyenNganhEntity insert(@Valid CreateChuyenNganhCommand createChuyenNganhRequest){
        ChuyenNganhEntity chuyenNganhEntity = formUtils.convertToObject(ChuyenNganhEntity.class,createChuyenNganhRequest);
        return chuyenNganhRepository.save(chuyenNganhEntity);
    }

    @Override
    public ChuyenNganhEntity search(String id) {
        Optional<ChuyenNganhEntity> chuyenNganh = chuyenNganhRepository.findById(id);
        if(Objects.isNull(chuyenNganh)) {
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }
        return chuyenNganh.get();
    }

    @Override
    public List<ChuyenNganh> getListChuyenNganhCustom() {
        return chuyenNganhRepository.getListChuyenNganhCustom();
    }

}
