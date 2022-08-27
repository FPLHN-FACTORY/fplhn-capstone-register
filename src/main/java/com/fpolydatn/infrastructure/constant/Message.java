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
    PHAN_CONG_HUONG_DAN_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.PHAN_CONG_HUONG_DAN_NOT_EXIST)),
    HOC_KY_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.HOC_KY_NOT_EXIST)),
    TAI_KHOANG_KHONG_THE_DANG_NHAP_VAO_HE_THONG(PropertiesReader.getProperty(PropertyKeys.TAI_KHOANG_KHONG_THE_DANG_NHAP_VAO_HE_THONG)),
    NUMBER_FORMAT_EXCEPTION(PropertiesReader.getProperty(PropertyKeys.NUMBER_FORMAT_EXCEPTION)),
    NHOM_DATN_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.NHOM_DATN_NOT_EXIST)),
    STUDENT_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.STUDENT_NOT_EXIST)),
    STUDENT_HAVE_GROUP(PropertiesReader.getProperty(PropertyKeys.STUDENT_HAVE_GROUP)),
    GROUP_NO_EXIST(PropertiesReader.getProperty(PropertyKeys.GROUP_NO_EXIST)),
    OVERLOADED_GROUP(PropertiesReader.getProperty(PropertyKeys.OVERLOADED_GROUP)),

    GIANG_VIEN_HUONG_DAN_EXIST(PropertiesReader.getProperty(PropertyKeys.GIANG_VIEN_HUONG_DAN_EXIST)),

    MON_DATN_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.MON_DATN_NOT_EXIST)),
    MON_DATN_ALREADY_EXIST(PropertiesReader.getProperty(PropertyKeys.MON_DATN_ALREADY_EXIST)),

    EMAIL_FPT_EXIST(PropertiesReader.getProperty(PropertyKeys.EMAIL_FPT_EXIST)),
    TEN_TAI_KHOAN_GIANG_VIEN_HUONG_DAN_EXIST(PropertiesReader.getProperty(PropertyKeys.TEN_TAI_KHOAN_GIANG_VIEN_HUONG_DAN_EXIST)),
    EMAIL_FE_EXIST(PropertiesReader.getProperty(PropertyKeys.EMAIL_FE_EXIST)),
    NHOM_HUONG_DAN_TOI_DA(PropertiesReader.getProperty(PropertyKeys.NHOM_HUONG_DAN_TOI_DA));


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
