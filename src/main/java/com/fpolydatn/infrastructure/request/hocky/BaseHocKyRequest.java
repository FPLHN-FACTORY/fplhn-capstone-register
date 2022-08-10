package com.fpolydatn.infrastructure.request.hocky;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author HangNT
 */
@Validated
@Getter
@Setter
public abstract class BaseHocKyRequest {

    @NotNull(message = "Ten Hoc Ky Chua Khoang Trang")
    @NotEmpty(message = "Ten Hoc Ky Trong")
    @Min(value = 6,message = "Tên học kỳ tối thiếu 6 ký tự")
    private String tenHocKy;

}
