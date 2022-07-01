package com.fpolydatn.infrastructure.exception;

import com.fpolydatn.infrastructure.constant.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class RestApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer status;

    private String message;

    public RestApiException() {
    }

    public RestApiException(StatusCode statusCode) {
        this.status = statusCode.getStatus();
        this.message = statusCode.getMessage();
    }

    public RestApiException(String message) {
        this.status = StatusCode.ERROR_UNKNOWN.getStatus();
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
