package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.CoSo;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author Vinhnv
 */
@Projection(types = {GiangVienHuongDan.class, DotDangKy.class, CoSo.class})
public interface DtGiangVienHDResponse extends IsIdentified {

    Integer getSTT();

    @Value("#{target.ten_co_so}")
    String getTenCoSo();

    @Value("#{target.ten_dot_dang_ky}")
    String getTenDotDangKy();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.so_nhom_huong_dan_toi_da}")
    Short getSoNhomHuongDanToiDa();

    @Value("#{target.so_nhom_dang_huong_dan}")
    Short getSoNhomDangHuongDan();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.so_dien_thoai}")
    String getSoDienThoai();

    @Value("#{target.email_fpt}")
    String getEmailFpt();

    @Value("#{target.email_fe}")
    String getEmailFe();

}
