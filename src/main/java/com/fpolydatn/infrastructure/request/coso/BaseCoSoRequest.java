package com.fpolydatn.infrastructure.request.coso;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author phongtt35
 */
@Getter
@Setter
public abstract class BaseCoSoRequest {

    @NotEmpty
    @NotBlank
    private String tenCoSo;

}
