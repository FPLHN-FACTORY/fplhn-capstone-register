package com.fpolydatn.core.giangvien.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangdt
 */

@Getter
@Setter
public final class GvFindNhomDatnRequest extends PageableRequest {

    private String giangVienHDId;
    private String monId;
    private String coSoId;

}
