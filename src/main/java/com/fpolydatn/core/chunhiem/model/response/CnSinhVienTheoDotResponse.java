package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SinhVien.class, ChuyenNganh.class, MonDatn.class, NhomDatn.class, GiangVienHuongDan.class, DotDangKy.class})
public interface CnSinhVienTheoDotResponse {

    Integer getStt();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.ma_mon_chuong_trinh}")
    String getMaMonChuongTrinh();

    @Value("#{target.ma_mon_du_an}")
    String getMaMonDuAn();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();
}
