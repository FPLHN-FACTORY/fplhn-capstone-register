package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {SinhVien.class, MonDatn.class, NhomDatn.class, GiangVienHuongDan.class, DotDangKy.class})
public interface CnSinhVienTheoDotExcel {

    Integer getStt();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();

    @Value("#{target.ten_sinh_vien}")
    String getTenSinhVien();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.chuc_vu}")
    String getChucVu();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.gvhd}")
    String getGvhd();

    @Value("#{target.ten_dot_dang_ky}")
    String getTenDotDangKy();

}
