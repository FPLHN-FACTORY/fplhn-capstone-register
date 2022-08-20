package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.entity.CoSo;
import com.fpolydatn.infrastructure.constant.VaiTro;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

/**
 * @author caodinh
 */
@Projection(types = {VaiTro.class, CoSo.class})
public interface DtCanBoResponse {

    Integer getSTT();

    String getId();

    @Value("#{target.email_fe}")
    String getEmailFE();

    @Value("#{target.email_fpt}")
    String getEmailFPT();

    @Value("#{target.so_dien_thoai}")
    String getSoDienThoai();

    @Value("#{target.ten_can_bo}")
    String getTenCanBo();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();

    @Value("#{target.ten_co_so}")
    String getTenCoSo();

}
