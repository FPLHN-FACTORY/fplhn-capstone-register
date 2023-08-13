package com.fpolydatn.core.chunhiem.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface CnGiangVienHuongDanDanhSachNhomResponse {

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.danh_sach_nhom}")
    String getDanhSachNhom();
}
