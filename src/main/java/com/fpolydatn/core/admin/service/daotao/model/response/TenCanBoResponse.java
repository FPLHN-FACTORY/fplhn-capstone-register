package com.fpolydatn.core.admin.service.daotao.model.response;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author hoangnt
 */
@Projection(types = {CanBo.class})
public interface TenCanBoResponse extends IsIdentified {

    @Value("#{target.ten_can_bo}")
    String getTenCanBo();

}
