package com.fpolydatn.core.sinhvien.model.response;

import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author hoangnt
 */
@Projection(types = {NhomDatn.class, SinhVien.class, GiangVienHuongDan.class, DotDangKy.class})
public interface SvDetailNhomDatnResponse extends IsIdentified {

    @Value("#{target.id_sinh_vien}")
    String getIdSinhVien();

    Integer getStt();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_nhom}")
    String getTenNhom();

    @Value("#{target.ten_dot_dang_ky}")
    String getTenDotDangKy();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.ten_mon_datn}")
    String getTenMonDatn();

    @Value("#{target.ten_mon_chuong_trinh}")
    String getTenMonChuongTrinh();

    @Value("#{target.chuc_vu}")
    Integer getChucVu();

    @Value("#{target.trang_thai}")
    Integer getTrangThai();

    @Value("#{target.nhan_xet}")
    String getNhanXet();

    @Value("#{target.mat_khau}")
    String getMatKhau();

}
