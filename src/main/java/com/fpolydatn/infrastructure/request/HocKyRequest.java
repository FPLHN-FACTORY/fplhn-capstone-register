package com.fpolydatn.infrastructure.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
public class HocKyRequest {

    private String tenHocKy;

}
