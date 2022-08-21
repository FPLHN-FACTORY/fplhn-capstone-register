package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author caodinh
 */

@Projection(types = {CanBo.class})
public interface DtTenCanBoResponse extends IsIdentified {

    @Value("#{target.ten_can_bo}")
    String getTenCanBo();

}

