package com.fpolydatn.core.admin.model.response;

import com.fpolydatn.entity.base.IsIdentified;
import com.fpolydatn.entity.HocKy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author phongtt35
 */

@Projection(types = {HocKy.class})
public interface HocKyResponse extends IsIdentified {

    @Value("#{target.ten_hoc_ky}")
    String getTenHocKy();
}
