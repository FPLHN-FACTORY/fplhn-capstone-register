package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {GiangVienHuongDan.class})
public interface CnGiangVienHuongDanResponse extends IsIdentified {

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.so_nhom_huong_dan_toi_da}")
    Integer getSoNhomHuongDanToiDa();
}
