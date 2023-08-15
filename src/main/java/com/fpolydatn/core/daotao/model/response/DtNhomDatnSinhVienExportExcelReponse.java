package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {MonDatn.class, ChuyenNganh.class, NhomDatn.class, SinhVien.class,
        GiangVienHuongDan.class, DotDangKy.class, HocKy.class})
public interface DtNhomDatnSinhVienExportExcelReponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.ten_chuyen_nganh}")
    String getChuyenNganh();

    @Value("#{target.ten_mon_datn}")
    String getMonDatn();

    @Value("#{target.nhom_id}")
    String getNhomId();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_mon_datn}")
    String getTenMonDatn();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.trang_thai}")
    int getTrangThai();

    @Value("#{target.truong_nhom_id}")
    String getTruongNhomId();
}
