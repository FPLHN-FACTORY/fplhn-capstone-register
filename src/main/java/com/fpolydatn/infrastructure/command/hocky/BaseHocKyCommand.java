package com.fpolydatn.infrastructure.command.hocky;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author HangNT
 */
@Validated
@Getter
@Setter
public abstract class BaseHocKyCommand {

    @NotEmpty()
    @NotBlank()
    @Length(min = 6, max = EntityProperties.LENGTH_NAME_SHORT)
    private String tenHocKy;

}
