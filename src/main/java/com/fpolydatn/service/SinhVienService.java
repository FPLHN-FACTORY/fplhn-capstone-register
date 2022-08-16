package com.fpolydatn.service;

import com.fpolydatn.infrastructure.response.SinhVienResponse;

/**
 * @author HangNT
 */
public interface SinhVienService {

    SinhVienResponse findSinhVienByEmailAndCoSo(String coSoID, String email);

}
