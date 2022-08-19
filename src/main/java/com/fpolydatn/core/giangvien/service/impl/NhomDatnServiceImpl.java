package com.fpolydatn.core.giangvien.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.UpdateNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.NhomDatnResponse;
import com.fpolydatn.core.giangvien.repository.GvNhomDatnRepository;
import com.fpolydatn.core.giangvien.service.NhomDatnService;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author thangdt
 */

@Service
public class NhomDatnServiceImpl implements NhomDatnService {

    @Autowired
    private GvNhomDatnRepository nhomDatnRepository;

    @Override
    public PageableObject<NhomDatnResponse> fillAllNhomDatn( FindNhomDatnRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<NhomDatnResponse> responses = nhomDatnRepository.fillAllNhomDatn(request, pageable);
        return new PageableObject(responses);
    }

    @Override
    public List<String> listHocKy() {
        return nhomDatnRepository.getAllHocKy();
    }

    @Override
    public List<String> listDotDangKi() {
        return nhomDatnRepository.getAllDotdangKy();
    }

    @Override
    public List<String> listChuyenNganh() {
        return nhomDatnRepository.getAllChuyenNganh();
    }

    @Override
    public NhomDatn updateTrangThaiNhom(final UpdateNhomDatnRequest request) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(request.getMaNhom());
        if (Objects.isNull(nhomDatnCheck)) {
//            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.GVHD_CHOT_DE_TAI);
        return nhomDatnRepository.save(nhomDatnCheck.get());
    }

}
