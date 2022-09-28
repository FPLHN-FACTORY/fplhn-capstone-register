package com.fpolydatn.core.sinhvien.model.request;

import lombok.Data;

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
    private String tenDeTai;
}
