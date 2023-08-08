package com.fpolydatn.core.sinhvien.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author SonPT
 */

@Data
public class SvUpdateTenDeTaiRequest{

    private String idUser;

    @NotEmpty()
    @NotBlank()
    @Length(max = 255)
    private String tenDeTai;
}
