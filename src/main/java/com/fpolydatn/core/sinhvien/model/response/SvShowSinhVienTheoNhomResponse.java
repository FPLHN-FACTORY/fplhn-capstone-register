package com.fpolydatn.core.sinhvien.model.response;

import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */

@Projection(types = {SinhVien.class, NhomDatn.class})
public interface SvShowSinhVienTheoNhomResponse {

    @Value("#{target.stt}")
    int getStt();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.chuc_vu}")
    Integer getChucVu();

    @Value("#{target.mo_ta}")
    String getMoTa();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_nhom}")
    String getTenNhom();

}
