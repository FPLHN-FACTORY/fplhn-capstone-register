package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.FindSinhVienRequest;
import com.fpolydatn.core.chunhiem.model.response.SinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.repository.CNSinhVienTheoNhomRepository;
import com.fpolydatn.core.chunhiem.service.SinhVienTheoNhomService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SinhVienTheoNhomServiceImpl implements SinhVienTheoNhomService {

    @Autowired
    private CNSinhVienTheoNhomRepository cnSinhVienTheoNhomRepository;


    @Override
    public PageableObject<SinhVienTheoNhomResponse> searchByIdNhom(final FindSinhVienRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<SinhVienTheoNhomResponse> res =cnSinhVienTheoNhomRepository.searchSinhVien(request,pageable);
        return new PageableObject<>(res);
    }

    @Override
    public boolean addSinhVienTheoNhom(String maNhom, String maSinhVien) {
        String id = cnSinhVienTheoNhomRepository.getIdSinhVienByMaSinhVien(maSinhVien);
        if(id==null){
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        String idExist = cnSinhVienTheoNhomRepository.getIdbyMaSinhVienAndMaNhom(maSinhVien);
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

}
