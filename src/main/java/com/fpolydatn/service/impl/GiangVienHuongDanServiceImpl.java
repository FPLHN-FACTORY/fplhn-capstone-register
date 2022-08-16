package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.response.GiangVienHuongDanResponse;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.service.GiangVienHuongDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author HangNT
 */
@Service
public class GiangVienHuongDanServiceImpl implements GiangVienHuongDanService {

    @Autowired
    private GiangVienHuongDanRepository giangVienHuongDanRepository;

    @Override
    public GiangVienHuongDanResponse findGiangVienByEmailAndCoSo(String id, String emailFPT) {
        GiangVienHuongDanResponse response = giangVienHuongDanRepository.findGiangVienByEmailAndCoSo(id, emailFPT);
        if (Objects.isNull(response)) {
            throw new RestApiException(Message.TAI_KHOANG_KHONG_THE_DANG_NHAP_VAO_HE_THONG);
        }
        return response;
    }

}
