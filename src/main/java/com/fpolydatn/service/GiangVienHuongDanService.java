package com.fpolydatn.service;

import com.fpolydatn.model.domain.GiangVienHuongDan;

/**
 * @author HangNT
 */
public interface GiangVienHuongDanService {

    GiangVienHuongDan findGiangVienByEmailAndCoSo(String coSoID, String emailFPT);

}
