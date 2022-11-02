package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.entity.base.IsIdentified;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thepvph20110
 */
@Projection(types = {SinhVien.class, NhomDatn.class,
        ChuyenNganh.class, MonDatn.class, GiangVienHuongDan.class, DotDangKy.class})
public interface DtSinhVienTheoDotResponse extends IsIdentified {

    @Value("#{target.stt}")
    int getStt();

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

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.ten_mon_datn}")
    String getTenMonDatn();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.ten_dot_dang_ky}")
    String getTenDotDangKy();

    @Value("#{target.trang_thai}")
    int getTrangThai();

    @Value("#{target.so_thanh_vien}")
    Long getSoThanhVien();

}
