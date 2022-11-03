package com.fpolydatn.core.daotao.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Vinhnv
 */
@Getter
@Setter
public final class DtFindGiangVienHDRequest extends PageableRequest {

    private String tenGiangVien;

    private String coSoId;

    private String dotDangKyId;

}
