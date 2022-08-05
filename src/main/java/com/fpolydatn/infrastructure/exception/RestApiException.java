package com.fpolydatn.infrastructure.exception;

import com.fpolydatn.infrastructure.constant.Mesage;
import org.springframework.stereotype.Component;

@Component
public class RestApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public RestApiException() {
    }

    public RestApiException(Mesage statusCode) {
        this.message = statusCode.getMessage();
    }

    public RestApiException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
