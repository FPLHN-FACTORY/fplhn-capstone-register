package com.fpolydatn.infrastructure.constant;


import com.fpolydatn.util.PropertiesReader;

public enum StatusCode {

    SUCCESS(200, "Success"),
    ERROR_UNKNOWN(1, "Error Unknown"),
    DATA_EMPTY(400, PropertiesReader.getProperty(PropertyKeys.DATA_EMPTY)),

    NAME_EMPTY(401, PropertiesReader.getProperty(PropertyKeys.NAME_EMPTY)),

    USERNAME_EMPTY(402, PropertiesReader.getProperty(PropertyKeys.USERNAME_EMPTY)),

    PHONE_NUMBER_EMPTY(403, PropertiesReader.getProperty(PropertyKeys.PHONE_NUMBER_EMPTY)),

    EMAIL_FPT_EMPTY(404, PropertiesReader.getProperty(PropertyKeys.EMAIL_FPT_EMPTY)),

    EMAIL_FE_EMPTY(405, PropertiesReader.getProperty(PropertyKeys.EMAIL_FE_EMPTY)),

    NAME_ROLE_EMPTY(406, PropertiesReader.getProperty(PropertyKeys.NAME_ROLE_EMPTY)),

    PHONE_NUMBER_FORMAT(407, PropertiesReader.getProperty(PropertyKeys.PHONE_NUMBER_FORMAT)),

    EMAIL_FPT_FORMAT(408, PropertiesReader.getProperty(PropertyKeys.EMAIL_FPT_FORMAT)),

    EMAIL_FE_FORMAT(409, PropertiesReader.getProperty(PropertyKeys.EMAIL_FE_FORMAT)),

    ID_ROLE_EMPTY(410, PropertiesReader.getProperty(PropertyKeys.ID_ROLE_EMPTY)),

    ID_ROLE_NOT_FOUND(411, PropertiesReader.getProperty(PropertyKeys.ID_ROLE_NOT_FOUND)),

    ID_FACILITIES_EMPTY(412, PropertiesReader.getProperty(PropertyKeys.ID_FACILITIES_EMPTY)),

    ID_FACILITIES_NOT_FOUND(413, PropertiesReader.getProperty(PropertyKeys.ID_FACILITIES_NOT_FOUND)),

    ID_CADRES_EMPTY(414, PropertiesReader.getProperty(PropertyKeys.ID_CADRES_EMPTY)),

    ID_CADRES_NOT_FOUND(415, PropertiesReader.getProperty(PropertyKeys.ID_CADRES_NOT_FOUND));

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
