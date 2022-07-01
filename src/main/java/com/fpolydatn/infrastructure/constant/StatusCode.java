package com.fpolydatn.infrastructure.constant;


import com.fpolydatn.util.PropertiesReader;

public enum StatusCode {

    SUCCESS(200, "Success"),
    ERROR_UNKNOWN(1, "Error Unknown"),
    ABSENT_DETAIL_NOT_EXIST(400, PropertiesReader.getProperty(PropertyKeys.ABSENT_DETAIL_NOT_EXIST));

    private Integer status;

    private String message;

    StatusCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
