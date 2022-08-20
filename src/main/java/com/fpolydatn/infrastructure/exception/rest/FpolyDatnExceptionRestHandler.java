package com.fpolydatn.infrastructure.exception.rest;

import com.fpolydatn.infrastructure.exception.FpolyDatnExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author phongtt35
 */

public abstract class FpolyDatnExceptionRestHandler<Z extends Exception>
        extends FpolyDatnExceptionHandler<ResponseEntity<?>,Z>{

    @Override
    protected ResponseEntity<?> wrap(Z ex) {
        return new ResponseEntity<>(wrapApi(ex), HttpStatus.BAD_REQUEST);
    }

    protected abstract Object wrapApi(Z ex);
}
