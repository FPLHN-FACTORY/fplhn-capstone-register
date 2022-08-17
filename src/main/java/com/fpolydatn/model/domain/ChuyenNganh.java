package com.fpolydatn.model.domain;

import com.fpolydatn.model.base.IsIdentified;
import com.fpolydatn.model.entity.ChuyenNganhEntity;
import com.fpolydatn.model.entity.CoSoEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

/**
 * @author thangncph26123
 */
@Projection(types = { ChuyenNganhEntity.class, CoSoEntity.class })
public interface ChuyenNganh extends IsIdentified {

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.ten_co_so}")
    String getTenCoSo();

}
