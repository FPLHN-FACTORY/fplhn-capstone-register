package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface CnNhomTheoDotExcel extends IsIdentified {

    @Value("#{target.STT}")
    Integer getStt();

    @Value("#{target.ma_nhom}")
    String getMaNhom();

    @Value("#{target.ten_de_tai}")
    String getTenDeTai();

    @Value("#{target.ten_tai_khoan}")
    String getGvhd();

}
