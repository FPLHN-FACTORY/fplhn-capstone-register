package com.fpolydatn.core.giangvien.model.response;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangdt
 */

@Projection(types = {NhomDatn.class, SinhVien.class, ChuyenNganh.class, GiangVienHuongDan.class})
public interface GvNhomDatnResponse {

    @Value("#{target.stt}")
    Integer getStt();

    String getId();

    @Value("#{target.ten_nhom}")
    String getTenNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.trang_thai}")
    int getTrangThai();

    @Value("#{target.total_ma_sinh_vien}")
    String getTotalMaSinhVien();

}
