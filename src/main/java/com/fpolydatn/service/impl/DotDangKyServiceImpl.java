package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateDotDangKyRequest;
import com.fpolydatn.model.CoSo;
import com.fpolydatn.model.DotDangKy;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.service.DotDangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.springframework.util.ObjectUtils;

import java.util.UUID;
import java.util.List;

/**
 * @author thepvph20110
 */

@Service
public class DotDangKyServiceImpl implements DotDangKyService {

    @Autowired
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    @Override
    public DotDangKy deleteDotDangKy(UUID id) {
        Optional<DotDangKy> dotDangKy = dotDangKyRepository.findById(id);
        if (!dotDangKy.isPresent()) {
            throw new RestApiException(Message.ID_DOTDANGKY_EMPTY);
        }
        dotDangKyRepository.delete(dotDangKy.get());
        return dotDangKy.get();
    }

    @Override
    public List<DotDangKy> getListDotDangKy(UUID coSoId, int offset, int size) {
        Optional<CoSo> coSo = coSoRepository.findById(coSoId);
        if (!coSo.isPresent()) {
            throw new RestApiException(Message.ID_COSO_NOT_EXIST);
        }
        String id = String.valueOf(coSoId);
        return dotDangKyRepository.getListDotDangKy(id, offset, size);
    }

    @Override
    public DotDangKy findByID(UUID id) {
        Optional<DotDangKy> dotDangKy = dotDangKyRepository.findById(id);
        if (!dotDangKy.isPresent()) {
            throw new RestApiException(Message.ID_DOTDANGKY_EMPTY);
        }
        return dotDangKy.get();
    }

    private DotDangKy convertToDotDangKy(CreateDotDangKyRequest dotDangKyRequest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DotDangKy dotDangKy = new DotDangKy();
        try {
            Date ngayBatDau = sdf.parse(dotDangKyRequest.getNgayBatDau());
            Date ngayKetThuc = sdf.parse(dotDangKyRequest.getNgayKetThuc());
            dotDangKy.setTenDotDangKy(dotDangKyRequest.getTenDotDangKy());
            dotDangKy.setNgayBatDau(ngayBatDau);
            dotDangKy.setNgayKetThuc(ngayKetThuc);
            dotDangKy.setHocKyId(dotDangKyRequest.getHocKyId());
            dotDangKy.setCoSoId(dotDangKyRequest.getCoSoId());
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return dotDangKy;
    }

    private void Regex(CreateDotDangKyRequest dotDangKyRequest, Optional<CoSo> coSo) {

        if (Objects.isNull(dotDangKyRequest)) {
            throw new RestApiException(Message.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(dotDangKyRequest.getTenDotDangKy())) {
            throw new RestApiException(Message.NAME_EMPTY);
        }
        if (ObjectUtils.isEmpty(dotDangKyRequest.getNgayBatDau())) {
            throw new RestApiException(Message.NGAY_BAT_DAU_EMPTY);
        }
        if (ObjectUtils.isEmpty(dotDangKyRequest.getNgayKetThuc())) {
            throw new RestApiException(Message.NGAY_KET_THUC_EMPTY);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date ngayBatDau = sdf.parse(dotDangKyRequest.getNgayBatDau());
            Date ngayKetThuc = sdf.parse(dotDangKyRequest.getNgayKetThuc());
            if (ngayBatDau.after(ngayKetThuc)) {
                throw new RestApiException(Message.NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

//        if (ObjectUtils.isEmpty(dotDangKyRequest.getHocKyId())) {
//            throw new RestApiException(StatusCode.ID_HOC_KY_EMPLY);
//        }
        if (ObjectUtils.isEmpty(dotDangKyRequest.getCoSoId())) {
            throw new RestApiException(Message.ID_CO_SO_EMPLY);
        }
        if (!coSo.isPresent()) {
            throw new RestApiException(Message.ID_COSO_NOT_EXIST);
        }
    }

}
