package com.fpolydatn.service;

import com.fpolydatn.model.domain.SinhVien;

/**
 * @author HangNT
 */
public interface SinhVienService {

    SinhVien findSinhVienByEmailAndCoSo(String coSoID, String email);

}
