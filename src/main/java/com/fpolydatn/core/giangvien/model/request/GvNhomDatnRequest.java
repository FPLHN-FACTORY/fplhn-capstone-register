package com.fpolydatn.core.giangvien.model.request;

import com.fpolydatn.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangdt
 */
@Getter
@Setter
public class GvNhomDatnRequest extends PageableRequest {

    private String maGvhd;
}
