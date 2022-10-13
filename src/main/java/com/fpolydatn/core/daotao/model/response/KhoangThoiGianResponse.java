package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.DotDangKy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {DotDangKy.class})
public interface KhoangThoiGianResponse {

    @Value("#{target.ngay_bat_dau}")
    Long getNgayBatDau();

    @Value("#{target.ngay_ket_thuc}")
    Long getNgayKetThuc();
}
