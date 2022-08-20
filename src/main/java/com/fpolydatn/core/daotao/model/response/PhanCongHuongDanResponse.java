package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.PhanCongHuongDan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author huynq
 */
@Projection(types = {PhanCongHuongDan.class})
public interface PhanCongHuongDanResponse {

    Integer getStt();
    @Value("#{target.id}")
    String getId();

    @Value("#{target.ten_gvhd}")
    String getTenGV();
}
