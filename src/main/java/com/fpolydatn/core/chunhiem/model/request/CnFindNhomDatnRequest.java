package com.fpolydatn.core.chunhiem.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public final class CnFindNhomDatnRequest extends PageableRequest {

    private String maNhom;

    private String tenDeTai;

    private String tenTaiKhoan;

    private String monDatnId;

    private String trangThai;

    private String dotDangKyId;

    private String coSoId;

}
