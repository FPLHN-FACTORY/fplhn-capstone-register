package com.fpolydatn.service;

import com.fpolydatn.infrastructure.response.GiangVienHuongDanResponse;

/**
 * @author HangNT
 */
public interface GiangVienHuongDanService {

    GiangVienHuongDanResponse findGiangVienByEmailAndCoSo(String coSoID, String emailFPT);

}
