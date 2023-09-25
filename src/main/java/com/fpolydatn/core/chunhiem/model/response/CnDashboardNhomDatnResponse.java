package com.fpolydatn.core.chunhiem.model.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface CnDashboardNhomDatnResponse {

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.mon_datn}")
    String getMonDatn();

    @Value("#{target.so_luong_thanh_vien}")
    Integer getSoLuongThanhVien();
}
