package com.fpolydatn.core.sinhvien.model.response;

import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author ToanNT
 */

@Projection(types = {GiangVienHuongDan.class})
public interface SvGiangVienHuongDanResponse extends IsIdentified {

    @Value("#{target.ten_gvhd}")
    String getTenGvhd();

    @Value("#{target.so_nhom_huong_dan_toi_da}")
    Integer getSoNhomHuongDanToiDa();
}
