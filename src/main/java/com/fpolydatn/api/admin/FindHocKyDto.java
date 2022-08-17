package com.fpolydatn.api.admin;

import com.fpolydatn.api.base.PageableDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author phongtt35
 */

@Getter
@Setter
public final class FindHocKyDto extends PageableDto {

    private String tenHocKy;
}
