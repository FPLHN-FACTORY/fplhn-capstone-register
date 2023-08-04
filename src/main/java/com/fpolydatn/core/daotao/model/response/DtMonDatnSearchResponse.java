package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {MonDatn.class})
public interface DtMonDatnSearchResponse extends IsIdentified {

    @Value("#{target.ma_mon}")
    String getMaMonDatn();

    @Value("#{target.ten_mon_datn}")
    String getTenMonDatn();
}
