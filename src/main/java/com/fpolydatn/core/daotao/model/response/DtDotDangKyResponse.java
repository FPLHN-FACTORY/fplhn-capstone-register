package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */

@Projection(types = {DotDangKy.class, HocKy.class})
public interface DtDotDangKyResponse extends IsIdentified {

    Integer getSTT();

    @Value("#{target.ten_hoc_ky}")
    String getTenHocKy();

    @Value("#{target.ten_dot_dang_ky}")
    String getTenDotDangKy();

    @Value("#{target.ngay_bat_dau}")
    Long getNgayBatDau();

    @Value("#{target.ngay_ket_thuc}")
    Long getNgayKetThuc();

    @Value("#{target.trang_thai}")
    Integer getTrangThai();

    @Value("#{target.tong_sinh_vien}")
    Integer getTongSinhVien();

    @Value("#{target.tong_sv_chua_co_nhom}")
    Integer getTongSvChuaCoNhom();

    @Value("#{target.tong_so_nhom}")
    Integer getTongSoNhom();

    @Value("#{target.tong_nhom_khong_hop_le}")
    Integer getTongNhomKhongHopLe();

}
