package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.CoSo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import java.util.UUID;

@Projection(types = { ChuyenNganh.class, CoSo.class, CanBo.class})
public interface ChuyenNganhResponse {

    Integer getStt();
    UUID getId();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.ten_co_so}")
    String getTenCoSo();

    @Value("#{target.ten_can_bo}")
    String getTenCanBo();

}
