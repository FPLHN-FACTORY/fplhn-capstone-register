package com.fpolydatn.infrastructure.exception.rest;

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

    private String fieldError;

    private String message;

}
