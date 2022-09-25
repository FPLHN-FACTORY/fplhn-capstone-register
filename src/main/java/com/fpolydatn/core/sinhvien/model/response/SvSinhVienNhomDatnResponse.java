package com.fpolydatn.core.sinhvien.model.response;

import com.fpolydatn.entity.*;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(types = {ChuyenNganh.class, NhomDatn.class, SinhVien.class, GiangVienHuongDan.class, DotDangKy.class, HocKy.class})
public interface SvSinhVienNhomDatnResponse extends IsIdentified {

    @Value("#{target.stt}")
    int getStt();

    @Value("#{target.ten_nhom}")
    String getTenNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.total_ma_sinh_vien}")
    String getCountSinhVien();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.mat_khau}")
    String getMatKhau();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.trang_thai}")
    int getTrangThai();

}
