package com.fpolydatn.core.daotao.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * @author hungpv
 */
@Getter
@Setter
@Validated
public abstract class BaseMonDatnRequest {
    @NotEmpty
    @NotBlank
    private String maMon;

    @NotEmpty
    @NotBlank
    private String tenMonDatn;

    @NotEmpty
    @NotBlank
    private UUID coSoId;

    @NotEmpty
    @NotBlank
    private UUID chuyenNganhId;
}
