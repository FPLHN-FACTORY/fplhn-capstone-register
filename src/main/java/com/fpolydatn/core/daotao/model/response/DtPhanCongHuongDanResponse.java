package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author huynq
 */
@Projection(types = {PhanCongHuongDan.class})
public interface DtPhanCongHuongDanResponse extends IsIdentified {

    Integer getStt();

    @Value("#{target.ten_gvhd}")
    String getTenGV();

    @Value("#{target.ten_dot_dang_ky}")
    String getDotDangKy();

}
