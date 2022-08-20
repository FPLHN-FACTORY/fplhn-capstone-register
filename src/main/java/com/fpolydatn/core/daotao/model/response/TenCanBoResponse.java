package com.fpolydatn.core.daotao.model.response;


import com.fpolydatn.entity.CanBo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(types = {CanBo.class})
public interface TenCanBoResponse {

    UUID getId();

    @Value("#{target.ten_can_bo}")
    String getTenCanBo();
}

