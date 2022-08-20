package com.fpolydatn.infrastructure.exception.rest;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author phongtt35
 */
@RestControllerAdvice
public final class UnknownExceptionRestHandler extends
        FpolyDatnExceptionRestHandler<Exception> {

    @Override
    protected Object wrapApi(Exception ex) {
        return ex.getMessage();
    }
}
