package com.fpolydatn.infrastructure.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author phongtt35
 */

public abstract class FpolyDatnExceptionHandler<T, Z extends Exception> {

    @ExceptionHandler(Exception.class)
    public T handle(Z ex) {
        log(ex);
        return wrap(ex);
    }

    protected abstract T wrap(Z ex);

    private void log(Z ex) {
        System.out.println(ex.getMessage());
    }
}
