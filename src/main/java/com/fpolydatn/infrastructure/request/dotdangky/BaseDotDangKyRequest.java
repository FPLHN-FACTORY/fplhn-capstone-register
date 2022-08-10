package com.fpolydatn.infrastructure.request.dotdangky;

import com.fpolydatn.infrastructure.constant.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

/**
 * @author phongtt35
 */
@Getter
@Setter
public abstract class BaseDotDangKyRequest {

    @NotEmpty
    @NotBlank
    private String tenDotDangKy;

    @NotEmpty
    @NotBlank
    @Pattern(regexp=Constants.REGEX_DATE)
    private String ngayBatDau;

    @NotEmpty
    @NotBlank
    @Pattern(regexp=Constants.REGEX_DATE)
    private String ngayKetThuc;

    @NotEmpty
    @NotBlank
    private UUID hocKyId;

    private UUID coSoId;

}
