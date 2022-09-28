package com.fpolydatn.core.sinhvien.model.response;

import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author ToanNT
 */
@Projection(types = {MonDatn.class})
public interface SvMonDatnResponse extends IsIdentified {


    @Value("#{target.ma_mon}")
    String getMaMon();

    @Value("#{target.ten_mon_datn}")
    String getTenMon();

}
