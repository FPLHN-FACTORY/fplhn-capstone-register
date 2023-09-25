package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface CnSimpleDashboardGvhdResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.so_nhom_huong_dan_toi_da}")
    Integer getSoNhomHuongDanToiDa();

    @Value("#{target.mon_datn}")
    String getMonDatn();
}
