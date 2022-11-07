package com.fpolydatn.core.sinhvien.model.response;

import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {NhomDatn.class, SinhVien.class})
public interface SvSinhVienTheoNhomResponse {

    @Value("#{target.stt}")
    String getStt();

    @Value("#{target.chuc_vu}")
    String getChucVu();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.so_dien_thoai}")
    String getSoDienThoai();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.khoa}")
    String getKhoa();

    @Value("#{target.ma_mon}")
    String getMaMon();

}
