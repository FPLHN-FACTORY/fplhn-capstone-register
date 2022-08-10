package com.fpolydatn.infrastructure.request.hocky;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author HangNT
 */
@Validated
@Getter
@Setter
public abstract class BaseHocKyRequest {

    @NotEmpty
    @NotBlank
    @Min(6)
    private String tenHocKy;

}
