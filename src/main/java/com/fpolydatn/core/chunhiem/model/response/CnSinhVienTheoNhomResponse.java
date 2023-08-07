package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = { SinhVien.class, ChuyenNganh.class, DotDangKy.class, NhomDatn.class, GiangVienHuongDan.class})
public interface CnSinhVienTheoNhomResponse extends IsIdentified {

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

    @Value("#{target.ma_mon_chuong_trinh}")
    String getMaMonChuongTrinh();

}
