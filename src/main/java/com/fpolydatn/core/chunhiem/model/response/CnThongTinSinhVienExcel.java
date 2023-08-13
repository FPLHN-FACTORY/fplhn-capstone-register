package com.fpolydatn.core.chunhiem.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface CnThongTinSinhVienExcel {

    Integer getStt();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.so_dien_thoai}")
    String getSoDienThoai();

    @Value("#{target.khoa}")
    String getKhoa();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.mon_datn}")
    String getMonDatn();

    @Value("#{target.dot_dang_ky}")
    String getDotDangKy();

    @Value("#{target.ma_nhom}")
    String getMaNhom();
}
