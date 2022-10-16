package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author huynq
 */
@Projection(types = {MonDatn.class})
public interface DtPhanCongHuongDanMonDatnResponse extends IsIdentified {

    @Value("#{target.created_date}")
    String getCreateDate();

    @Value("#{target.last_modified_date}")
    String getLastModifiedDate();

    @Value("#{target.ten_co_so}")
    String getTenCoSo();

    @Value("#{target.ma_mon}")
    String getMaMon();

    @Value("#{target.ten_mon_datn}")
    String getTenMonDatn();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.co_so_id}")
    String getCoSoId();

    @Value("#{target.chuyen_nganh_id}")
    String getChuyenNganhId();

}
