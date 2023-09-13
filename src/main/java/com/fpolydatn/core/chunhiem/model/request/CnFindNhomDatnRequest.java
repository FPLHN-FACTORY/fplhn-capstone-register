package com.fpolydatn.core.chunhiem.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public final class CnFindNhomDatnRequest extends PageableRequest {

    private String maNhom;

    private String tenDeTai;

    private String tenTaiKhoan;

    private String maTruongNhom;

    private String monDatnId;

    private String trangThai;

    private String soThanhVien;

    private String sortNhomDatn;

    private String dotDangKyId;

    private String chuyenNganhId;

    private String coSoId;

    private Long currentTime = Calendar.getInstance().getTimeInMillis();

}
