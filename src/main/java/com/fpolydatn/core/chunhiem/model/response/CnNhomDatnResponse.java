package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {NhomDatn.class, SinhVien.class, ChuyenNganh.class, GiangVienHuongDan.class})
public interface CnNhomDatnResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ma_mon_datn}")
    String getMaMonDatn();

    @Value("#{target.total_ma_sinh_vien}")
    String getTotal();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.ten_dot_dang_ky}")
    String getTenDotDangKy();

    @Value("#{target.trang_thai}")
    int getTrangThai();

}
