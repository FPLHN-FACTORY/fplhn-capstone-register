package com.fpolydatn.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HangNT
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorModel {

    private String fileError;

    private String message;

}
