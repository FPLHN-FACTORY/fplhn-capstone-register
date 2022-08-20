package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.repository.DtGiangVienHDRepository;
import com.fpolydatn.core.daotao.service.DtGiangVienHDService;
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
public class DtGiangVienHDServiceImpl implements DtGiangVienHDService {

    @Autowired
    private DtGiangVienHDRepository dtGiangVienHDRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public GiangVienHuongDan create(DtCreateGiangVienHDRequest command) {
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
    public GiangVienHuongDan update(DtUpdateGiangVienHDRequest command) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(command.getId());
        if(!gvhdCheck.isPresent()){
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }
        GiangVienHuongDan giangVien = gvhdCheck.get();
        giangVien.setTenGvhd(command.getTenGvhd());
        giangVien.setSoNhomHuongDanToiDa(command.getSoNhomHuongDanToiDa());
        giangVien.setTenTaiKhoan(command.getTenTaiKhoan());
        giangVien.setSoDienThoai(command.getSoDienThoai());
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
    public PageableObject<DtGiangVienHDResponse> search(final DtFindGiangVienHDRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtGiangVienHDResponse> res = dtGiangVienHDRepository.findByName(request, pageable);
        return new PageableObject<>(res);
    }


}
