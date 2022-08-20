package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CnSinhVienTheoDotServiceImpl implements CnSinhVienTheoDotService {

    @Autowired
    private CnSinhVienRepository cnSinhVienTheoNhomRepository;

    @Autowired
    private CnNhomDatnRepository nhomDatnRepository;

    @Override
    public PageableObject<CnSinhVienTheoDotResponse> searchByIdDotDangKy(CnFindSinhVienTheoDotRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnSinhVienTheoDotResponse> res =cnSinhVienTheoNhomRepository.searchSinhVien(request,pageable);
        return new PageableObject<>(res);
    }

    @Override
    public boolean addSinhVienTheoNhom(String maNhom, String maSinhVien) {
        String idNhom = nhomDatnRepository.getIdByMaNhom(maNhom);
        if(idNhom==null){
            throw new RestApiException(Message.GROUP_NO_EXIST);
        }
        int numberOfGroup = nhomDatnRepository.getSoLuongThanhVienNhom(idNhom);
        if(numberOfGroup > 7){
            throw new RestApiException(Message.OVERLOADED_GROUP);
        }
        cnSinhVienTheoNhomRepository.addSinhVienInGroup(idNhom,maSinhVien);
        return true;
    }

    @Override
    public List<CnSinhVienTheoDotResponse> getSinhVienByTenSinhVien(String tenSinhVien,String coSoId) {
       return cnSinhVienTheoNhomRepository.getSinhVienByTenSinhVien(tenSinhVien,coSoId);
    }
}
