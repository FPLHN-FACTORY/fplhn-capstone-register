package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CnSinhVienTheoNhomServiceImpl implements CnSinhVienTheoNhomService {

    @Autowired
    private CnSinhVienRepository cnSinhVienTheoNhomRepository;

    @Autowired
    private CnGiangVienHuongDanReponsitory cnGiangVienHuongDanReponsitory;

    @Autowired
    private CnNhomDatnRepository cnNhomDatnRepository;

    @Override
    public PageableObject<CnSinhVienTheoNhomResponse> searchByIdNhom(final CnFindSinhVienTheoNhomRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnSinhVienTheoNhomResponse> res =cnSinhVienTheoNhomRepository.searchSinhVien(request,pageable);
        return new PageableObject<>(res);
    }

    @Override
    public boolean addSinhVienTheoNhom(String maNhom, String maSinhVien) {
        String id = cnSinhVienTheoNhomRepository.getIdSinhVienByMaSinhVien(maSinhVien);
        if(id==null){
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        String idExist = cnSinhVienTheoNhomRepository.getIdByMaSinhVienAndMaNhom(maSinhVien);
        if(idExist == null){
            throw new RestApiException(Message.STUDENT_HAVE_GROUP);
        }
        cnSinhVienTheoNhomRepository.addSinhVienInGroup(maNhom,maSinhVien);
        return true;
    }

    @Override
    public long getSizeListSinhVien(String idNhom) {
        return cnSinhVienTheoNhomRepository.countListSinhVien(idNhom);
    }

    @Override
    public boolean deleteFromIdSinhVien(String idNhom) {
        cnSinhVienTheoNhomRepository.deleteSinhVienOutGroup(idNhom);
        return true;
    }

    @Override
    public boolean addGvhd(String tenTaiKhoan, String idNhom) {
        String idGiangVien = cnGiangVienHuongDanReponsitory.getIdFromGiangVienId(tenTaiKhoan);
        if(idGiangVien==null){
            throw  new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
        }
        cnNhomDatnRepository.addGvhd(idGiangVien,idNhom);
        return true;
    }

}
