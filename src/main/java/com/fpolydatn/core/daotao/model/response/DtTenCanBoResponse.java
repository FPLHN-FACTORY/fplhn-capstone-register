package com.fpolydatn.core.daotao.model.response;


import com.fpolydatn.entity.CanBo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {CanBo.class})
public interface DtTenCanBoResponse {

    String getId();

    @Value("#{target.ten_can_bo}")
    String getTenCanBo();
}

