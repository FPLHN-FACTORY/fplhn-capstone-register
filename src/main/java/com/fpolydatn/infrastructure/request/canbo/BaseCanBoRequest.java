package com.fpolydatn.infrastructure.request.canbo;

import com.fpolydatn.infrastructure.constant.Constants;
import com.fpolydatn.infrastructure.constant.VaiTro;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Getter
@Setter
@Validated
public abstract class BaseCanBoRequest {

    @NotEmpty
    @NotBlank
    private String tenCanBo;

    @NotEmpty
    @NotBlank
    private String tenTaiKhoan;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = Constants.REGEX_PHONE_NUMBER)
    private String soDienThoai;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = Constants.REGEX_EMAIL_FPT)
    private String emailFpt;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = Constants.REGEX_EMAIL_FE)
    private String emailFe;

    @NotEmpty
    @NotBlank
    private UUID coSoId;

    private VaiTro vaiTro;

}
