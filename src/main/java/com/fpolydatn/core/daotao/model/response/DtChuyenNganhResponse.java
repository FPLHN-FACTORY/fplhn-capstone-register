package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author hoangnt
 */
@Projection(types = {ChuyenNganh.class, CoSo.class, CanBo.class})
public interface DtChuyenNganhResponse extends IsIdentified {

    Integer getStt();

    @Value("#{target.ten_chuyen_nganh}")
    String getTenChuyenNganh();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.ten_can_bo}")
    String getTenCanBo();

}
