package com.fpolydatn.core.daotao.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface DtNhomDatnExportResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_nhom}")
    String getTenNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.truong_nhom_id}")
    String getTruongNhomId();

    @Value("#{target.trang_thai}")
    int getTrangThai();
}
