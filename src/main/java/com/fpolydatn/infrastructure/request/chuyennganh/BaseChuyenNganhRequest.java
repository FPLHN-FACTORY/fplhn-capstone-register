package com.fpolydatn.infrastructure.request.chuyennganh;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Getter
@Setter
public abstract class BaseChuyenNganhRequest {

    @NotEmpty
    @NotBlank
    private String tenChuyenNganh;

    @NotEmpty
    @NotBlank
    private String chuyenNganhMeId;

    @NotEmpty
    @NotBlank
    private UUID coSoId;

}
