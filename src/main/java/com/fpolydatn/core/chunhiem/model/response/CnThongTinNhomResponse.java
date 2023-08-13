package com.fpolydatn.core.chunhiem.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface CnThongTinNhomResponse {

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_nhom}")
    String getTenNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.gvhd}")
    String getGvhd();

    @Value("#{target.mon_datn}")
    String getMonDatn();

    @Value("#{target.trang_thai}")
    Integer getTrangThai();
}
