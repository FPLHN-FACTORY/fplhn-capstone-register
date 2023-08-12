package com.fpolydatn.core.sinhvien.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author SonPT
 */

@Getter
@Setter
public class SvUpdateTenDeTaiRequest{

    private String idUser;

    @Length(max = 255)
    private String tenDeTai;
}
