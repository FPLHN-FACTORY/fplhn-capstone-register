package com.fpolydatn.infrastructure.command.chuyennganh;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Getter
@Setter
public abstract class BaseChuyenNganhCommand {

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
