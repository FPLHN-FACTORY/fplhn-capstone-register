package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {SinhVien.class})
public interface CnSinhVienSearchResponse extends IsIdentified {

    @Value("#{target.thong_tin}")
    String getThongTinSinhVien();

    @Value("#{target.ma_sinh_vien}")
    String getMaSinhVien();
}
