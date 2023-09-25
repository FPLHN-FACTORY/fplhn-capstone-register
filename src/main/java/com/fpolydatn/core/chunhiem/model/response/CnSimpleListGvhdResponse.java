package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface CnSimpleListGvhdResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.email_fpt}")
    String getEmail();

    @Value("#{target.so_dien_thoai}")
    String getSoDienThoai();

    @Value("#{target.so_nhom_huong_dan_toi_da}")
    Integer getSoNhomHuongDanToiDa();

    @Value("#{target.so_nhom_dang_huong_dan}")
    Integer getSoNhomDangHuongDan();

    @Value("#{target.mon_datn}")
    String getMonDatn();
}
