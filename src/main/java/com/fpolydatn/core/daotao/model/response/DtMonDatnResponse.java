package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author hungpv
 */
@Projection(types = {MonDatn.class, CanBo.class, ChuyenNganh.class})
public interface DtMonDatnResponse extends IsIdentified {

    Integer getStt();

    @Value("#{target.ma_mon}")
    String getMaMon();

    @Value("#{target.ten_mon_datn}")
    String getTenMon();

    @Value("#{target.ten_nhom_mon}")
    String getTenNhomMon();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

}
