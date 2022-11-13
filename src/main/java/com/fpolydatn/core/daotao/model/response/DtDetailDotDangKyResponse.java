package com.fpolydatn.core.daotao.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author SonPT
 */
public interface DtDetailDotDangKyResponse {

    @Value("#{target.ten_hoc_ky}")
    String getTenHocKy();

    @Value("#{target.ten_dot_dang_ky}")
    String getTenDotDangKy();

    @Value("#{target.ngay_bat_dau}")
    Long getNgayBatDau();

    @Value("#{target.ngay_ket_thuc}")
    Long getNgayKetThuc();

    @Value("#{target.han_sinh_vien}")
    Long getHanSinhVien();

    @Value("#{target.han_giang_vien}")
    Long getHanGiangVien();

    @Value("#{target.han_chu_nhiem_bo_mon}")
    Long getHanChuNhiemBoMon();

    @Value("#{target.tong_sinh_vien}")
    Integer getTongSinhVien();

    @Value("#{target.tong_sinh_vien_chua_co_nhom}")
    Integer getTongSinhVienChuaCoNhom();

    @Value("#{target.tong_sinh_vien_co_nhom}")
    Integer getTongSinhVienCoNhom();

    @Value("#{target.tong_so_nhom}")
    Integer getTongSoNhom();

    @Value("#{target.ti_le_xac_nhan}")
    String getTiLeXacNhan();

    @Value("#{target.trang_thai}")
    Integer getTrangThai();

}
