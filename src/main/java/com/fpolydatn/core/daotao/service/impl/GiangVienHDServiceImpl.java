package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.PageableRequest;
import com.fpolydatn.core.daotao.model.request.CreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.FindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.UpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.GiangVienHDResponse;
import com.fpolydatn.core.daotao.reopsitory.DtGiangVienHDRepository;
import com.fpolydatn.core.daotao.service.GiangVienHDService;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * @author Vinhnv
 */
@Service
@Validated
public class GiangVienHDServiceImpl implements GiangVienHDService {

    @Autowired
    private DtGiangVienHDRepository dtGiangVienHDRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public GiangVienHuongDan create(CreateGiangVienHDRequest command) {
        GiangVienHuongDan gvhd = formUtils.convertToObject(GiangVienHuongDan.class,command);
        return dtGiangVienHDRepository.save(gvhd);
    }

    @Override
    public Boolean delete(String id) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(id);
        if(!gvhdCheck.isPresent()){
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }
        dtGiangVienHDRepository.delete(gvhdCheck.get());
        return true;
    }

    @Override
    public GiangVienHuongDan update(UpdateGiangVienHDRequest command) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(command.getId());
        if(!gvhdCheck.isPresent()){
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }
        GiangVienHuongDan giangVien = gvhdCheck.get();
        giangVien.setTenGvhd(command.getTenGvhd());
        giangVien.setSoNhomHuongDanToiDa(command.getSoNhomHuongDanToiDa());
        giangVien.setTenTaiKhoan(command.getTenTaiKhoan());
        giangVien.setEmailFpt(command.getEmailFpt());
        giangVien.setEmailFe(command.getEmailFe());
        giangVien.setDotDangKyId(command.getDotDangKyId());
        return dtGiangVienHDRepository.save(giangVien);
    }

    @Override
    public GiangVienHuongDan findById(String id) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(id);
        if(!gvhdCheck.isPresent()){
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }
        return gvhdCheck.get();
    }

    @Override
    public long getSizeListHocKy(String tenGiangVien) {
        return dtGiangVienHDRepository.countByName(tenGiangVien);
    }

    @Override
    public PageableObject<GiangVienHDResponse> search(final FindGiangVienHDRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<GiangVienHDResponse> res = dtGiangVienHDRepository.findByName(request, pageable);
        return new PageableObject<>(res);
    }
}
