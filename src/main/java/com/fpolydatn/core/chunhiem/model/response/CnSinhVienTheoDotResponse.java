package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.core.entity.ChuyenNganh;
import com.fpolydatn.core.entity.GiangVienHuongDan;
import com.fpolydatn.core.entity.MonDatn;
import com.fpolydatn.core.entity.NhomDatn;
import com.fpolydatn.core.entity.SinhVien;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SinhVien.class, ChuyenNganh.class, MonDatn.class, NhomDatn.class, GiangVienHuongDan.class})
public interface CnSinhVienTheoDotResponse {

    Integer getStt();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.ma_mon}")
    String getMaMon();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();
}
