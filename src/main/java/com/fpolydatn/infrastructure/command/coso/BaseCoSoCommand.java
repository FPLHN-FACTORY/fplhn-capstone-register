package com.fpolydatn.infrastructure.command.coso;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author phongtt35
 */
@Getter
@Setter
public abstract class BaseCoSoCommand {

    @NotEmpty
    @NotBlank
    private String tenCoSo;

}
