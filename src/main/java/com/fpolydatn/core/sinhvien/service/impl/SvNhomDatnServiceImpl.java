package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.repository.SvNhomDatnRepository;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hoangnt
 */
@Service
public class SvNhomDatnServiceImpl implements SvNhomDatnService {

    @Autowired
    private SvNhomDatnRepository svNhomDatnRepository;

    @Override
    public List<SvNhomDatnResponse> getNhomDatnById(String id) {
        return svNhomDatnRepository.getNhomDatnById(id);
    }

    @Override
    public Boolean checkTruongNhom(String idNhomDatn, String idCoSo, String idUser) {
        String idTruongNhom = svNhomDatnRepository.getIdTruongNhom(idNhomDatn,idCoSo);
        if (idUser.equals(idTruongNhom)){
            return true;
        }
        return false;
    }

}
