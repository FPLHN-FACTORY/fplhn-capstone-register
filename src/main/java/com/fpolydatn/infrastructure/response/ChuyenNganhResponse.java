package com.fpolydatn.infrastructure.response;

import com.fpolydatn.model.domain.ChuyenNganh;
import com.fpolydatn.model.domain.CoSo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

/**
 * @author thangncph26123
 */
@Projection(types = { ChuyenNganh.class, CoSo.class })
public interface ChuyenNganhResponse {

      UUID getId();

    @Value("#{target.ten_chuyen_nganh}")
     String getTenChuyenNganh();

    @Value("#{target.ten_co_so}")
     String getTenCoSo();



}
