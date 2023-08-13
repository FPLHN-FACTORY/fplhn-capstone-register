package com.fpolydatn.core.sinhvien.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface SvSinhVienExportWordResponse {

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.so_dien_thoai}")
    String getSoDienThoai();

    @Value("#{target.email}")
    String getEmail();
}
