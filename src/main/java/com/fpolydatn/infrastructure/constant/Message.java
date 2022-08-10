package com.fpolydatn.infrastructure.constant;

import com.fpolydatn.util.PropertiesReader;

/**
 * @author nguyenvv4
 */
public enum Message {

    SUCCESS("Success"),

    ERROR_UNKNOWN("Error Unknown"),

    NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC(PropertiesReader.getProperty(PropertyKeys.NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC)),
    CHUYEN_NGANH_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.CHUYEN_NGANH_NOT_EXIST)),
    CO_SO_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.CO_SO_NOT_EXIST)),
    CAN_BO_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.CAN_BO_NOT_EXIST)),
    DOT_DANG_KY_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.DOT_DANG_KY_NOT_EXIST)),
    HOC_KY_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.HOC_KY_NOT_EXIST));

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
