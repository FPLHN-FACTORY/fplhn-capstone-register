package com.fpolydatn.model.domain;

import com.fpolydatn.model.base.IsIdentified;
import com.fpolydatn.model.entity.HocKyEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author phongtt35
 */

@Projection(types = {HocKyEntity.class})
public interface HocKy extends IsIdentified {

    @Value("#{target.ten_hoc_ky}")
    String getTenHocKy();
}
