package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.GiangVienHuongDan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {GiangVienHuongDan.class})
public interface CnGvhdSearchResponse {

    @Value("#{target.thong_tin}")
    String getThongTinGvhd();

    @Value("#{target.ten_tai_khoan}")
    String getTenTaiKhoan();
}
