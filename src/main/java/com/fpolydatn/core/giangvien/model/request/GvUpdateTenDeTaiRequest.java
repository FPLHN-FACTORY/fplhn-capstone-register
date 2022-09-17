package com.fpolydatn.core.giangvien.model.request;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author thangdt
 */

@Getter
@Setter
public final class GvUpdateTenDeTaiRequest {

    private String maNhom;

    @NotEmpty()
    @NotBlank()
    @Length(min = 6, max = EntityProperties.LENGTH_NAME_SHORT)
    private String tenDeTai;
}
