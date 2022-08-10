package com.fpolydatn.infrastructure.constant;

import com.fpolydatn.util.PropertiesReader;

/**
 * @author nguyenvv4
 */
public enum Message {

    SUCCESS("Success"),

    ERROR_UNKNOWN("Error Unknown"),
    DATA_EMPTY(PropertiesReader.getProperty(PropertyKeys.DATA_EMPTY)),

    NAME_EMPTY(PropertiesReader.getProperty(PropertyKeys.NAME_EMPTY)),

    TEN_CO_SO_EMPTY(PropertiesReader.getProperty(PropertyKeys.TEN_CO_SO_EMPTY)),

    USERNAME_EMPTY(PropertiesReader.getProperty(PropertyKeys.USERNAME_EMPTY)),

    PHONE_NUMBER_EMPTY(PropertiesReader.getProperty(PropertyKeys.PHONE_NUMBER_EMPTY)),

    EMAIL_FPT_EMPTY(PropertiesReader.getProperty(PropertyKeys.EMAIL_FPT_EMPTY)),

    EMAIL_FE_EMPTY(PropertiesReader.getProperty(PropertyKeys.EMAIL_FE_EMPTY)),

    DELETE_COSO_SUCCESS(PropertiesReader.getProperty(PropertyKeys.DELETE_COSO_SUCCESS)),

    ID_COSO_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.ID_COSO_NOT_EXIST)),

    NGAY_BAT_DAU_EMPTY(PropertiesReader.getProperty(PropertyKeys.NGAY_BAT_DAU_EMPTY)),

    NGAY_KET_THUC_EMPTY(PropertiesReader.getProperty(PropertyKeys.NGAY_KET_THUC_EMPTY)),

    NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC(PropertiesReader.getProperty(PropertyKeys.NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC)),

    NAME_ROLE_EMPTY(PropertiesReader.getProperty(PropertyKeys.NAME_ROLE_EMPTY)),

    PHONE_NUMBER_FORMAT(PropertiesReader.getProperty(PropertyKeys.PHONE_NUMBER_FORMAT)),

    EMAIL_FPT_FORMAT(PropertiesReader.getProperty(PropertyKeys.EMAIL_FPT_FORMAT)),

    EMAIL_FE_FORMAT(PropertiesReader.getProperty(PropertyKeys.EMAIL_FE_FORMAT)),

    ID_ROLE_EMPTY(PropertiesReader.getProperty(PropertyKeys.ID_ROLE_EMPTY)),

    ID_ROLE_NOT_FOUND(PropertiesReader.getProperty(PropertyKeys.ID_ROLE_NOT_FOUND)),

    ID_FACILITIES_EMPTY(PropertiesReader.getProperty(PropertyKeys.ID_FACILITIES_EMPTY)),

    ID_FACILITIES_NOT_FOUND(PropertiesReader.getProperty(PropertyKeys.ID_FACILITIES_NOT_FOUND)),

    ID_CADRES_EMPTY(PropertiesReader.getProperty(PropertyKeys.ID_CADRES_EMPTY)),

    ID_CADRES_NOT_FOUND(PropertiesReader.getProperty(PropertyKeys.ID_CADRES_NOT_FOUND)),

    ID_CO_SO_EMPLY(PropertiesReader.getProperty(PropertyKeys.ID_CO_SO_EMPLY)),

    ID_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.ID_NOT_EXISTS)),

    COSO_NAME_EMPTY(PropertiesReader.getProperty(PropertyKeys.COSO_NAME_EMPTY)),

    ID_DOTDANGKY_EMPTY(PropertiesReader.getProperty(PropertyKeys.ID_DOTDANGKY_EMPTY)),

    UUID_EMPTY(PropertiesReader.getProperty(PropertyKeys.UUID_EMPTY)),

    TEN_HOC_KY_EMPTY(PropertiesReader.getProperty(PropertyKeys.TEN_HOC_KY_EMPTY)),

    ID_IS_NOT_PRESENT(PropertiesReader.getProperty(PropertyKeys.ID_IS_NOT_PRESENT)),

    TEN_CHUYEN_NGANH_EMPTY(PropertiesReader.getProperty(PropertyKeys.TEN_CHUYEN_NGANH_EMPTY));

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
