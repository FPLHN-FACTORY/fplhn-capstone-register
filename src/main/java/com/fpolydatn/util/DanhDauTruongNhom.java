package com.fpolydatn.util;

import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


public class DanhDauTruongNhom {

    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    public String danhDauTruongNhom(String idNhom, String idSinhVien){

        return nhomDatnRepository.danhDauTruongNhom(idNhom,idSinhVien);

    }
}
