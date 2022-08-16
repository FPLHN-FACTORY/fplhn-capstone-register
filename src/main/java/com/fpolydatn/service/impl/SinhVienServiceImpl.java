package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.response.SinhVienResponse;
import com.fpolydatn.repository.SinhVienRepository;
import com.fpolydatn.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author HangNT
 */
@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Override
    public SinhVienResponse findSinhVienByEmailAndCoSo(String coSoID, String email) {
        SinhVienResponse sinhVienResponse = sinhVienRepository.findSinhVienByEmailAndCoSo(coSoID, email);
        if (Objects.isNull(sinhVienResponse)) {
            throw new RestApiException(Message.TAI_KHOANG_KHONG_THE_DANG_NHAP_VAO_HE_THONG);
        }
        return sinhVienResponse;
    }

}