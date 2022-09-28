package com.fpolydatn.core.giangvien.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.GvChotNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnHuongDanRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateTenDeTaiRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.repository.GvNhomDatnRepository;
import com.fpolydatn.core.giangvien.service.GvNhomDatnService;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

/**
 * @author thangdt
 */

@Service
public class GvNhomDatnServiceImpl implements GvNhomDatnService {

    @Autowired
    private GvNhomDatnRepository nhomDatnRepository;

    @Override
    public PageableObject<GvNhomDatnResponse> fillAllNhomDatn(final GvFindNhomDatnRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<GvNhomDatnResponse> responses = nhomDatnRepository.fillAllNhomDatn(request, pageable);
        return new PageableObject(responses);
    }

    @Override
    public NhomDatn updateTrangThaiNhom(final GvUpdateNhomDatnRequest request) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(request.getMaNhom());
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.GVHD_CHOT);
        return nhomDatnRepository.save(nhomDatnCheck.get());
    }

    @Override
    public NhomDatn findById(String maNhom) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(maNhom);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        return nhomDatnCheck.get();
    }

    @Override
    public NhomDatn updateTenDeTai(@Valid final GvUpdateTenDeTaiRequest request) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(request.getMaNhom());
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        nhomDatnCheck.get().setTenDeTai(request.getTenDeTai());
        return nhomDatnRepository.save(nhomDatnCheck.get());
    }

    @Override
    public PageableObject<GvNhomDatnResponse> findAllNhomDatnDoiPheDuyet(String maGvhd, final GvFindNhomDatnHuongDanRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<GvNhomDatnResponse> responses = nhomDatnRepository.findAllNhomDatnDoiPheDuyet(maGvhd, pageable);
        return new PageableObject(responses);
    }

    @Override
    public NhomDatn pheDuyetNhomDatn(GvChotNhomDatnRequest request) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(request.getMaNhom());
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.DA_LIEN_HE_GV);
        return nhomDatnRepository.save(nhomDatnCheck.get());
    }

    @Override
    public  PageableObject<GvNhomDatnResponse> findAllNhomDatnHuongDan(String maGvhd, final GvFindNhomDatnHuongDanRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<GvNhomDatnResponse> responses = nhomDatnRepository.findAllNhomDatnHuongDan(maGvhd,  pageable);
        return new PageableObject(responses);
    }

    @Override
    public NhomDatn pheDuyetDeTaiNhomDatn(GvChotNhomDatnRequest request) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(request.getMaNhom());
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.GVHD_CHOT);
        return nhomDatnRepository.save(nhomDatnCheck.get());
    }

    @Override
    public NhomDatn tuChoiDeTaiNhomDatn(GvChotNhomDatnRequest request) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(request.getMaNhom());
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.DA_LIEN_HE_GV);
        return nhomDatnRepository.save(nhomDatnCheck.get());
    }

}
