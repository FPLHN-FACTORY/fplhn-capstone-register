package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {MonDatn.class})
public interface DtNhomMonDatnResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.ma_mon}")
    String getMaNhomMon();

    @Value("#{target.ten_mon_datn}")
    String getTenNhomMon();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.so_mon_datn}")
    String getSoMon();

}
