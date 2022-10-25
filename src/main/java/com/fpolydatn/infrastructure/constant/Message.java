package com.fpolydatn.infrastructure.constant;

import com.fpolydatn.util.PropertiesReader;

/**
 * @author nguyenvv4
 */
public enum Message {

    SUCCESS("Success"),

    ERROR_UNKNOWN("Error Unknown"),

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
    GIANG_VIEN_HUONG_DAN_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.GIANG_VIEN_HUONG_DAN_NOT_EXIST)),
    DOT_DANG_KY_OVERLAP(PropertiesReader.getProperty(PropertyKeys.DOT_DANG_KY_OVERLAP)),
    GIANG_VIEN_DA_HUONG_DAN_DU_SO_NHOM(PropertiesReader.getProperty(PropertyKeys.GIANG_VIEN_DA_HUONG_DAN_DU_SO_NHOM)),
    MAT_KHAU_KHONG_CHINH_XAC(PropertiesReader.getProperty(PropertyKeys.MAT_KHAU_KHONG_CHINH_XAC)),
    EMPTY(PropertiesReader.getProperty(PropertyKeys.EMPTY)),
    EMAIL_MALFORMED(PropertiesReader.getProperty(PropertyKeys.EMAIL_MALFORMED)),
    MA_SV_MALFORMED(PropertiesReader.getProperty(PropertyKeys.MA_SV_MALFORMED)),
    FILE_EMPTY(PropertiesReader.getProperty(PropertyKeys.FILE_EMPTY)),
    TRANG_THAI_KHONG_THOA_MAN(PropertiesReader.getProperty(PropertyKeys.TRANG_THAI_KHONG_THOA_MAN)),
    KHONG_PHAI_TRUONG_NHOM(PropertiesReader.getProperty(PropertyKeys.KHONG_PHAI_TRUONG_NHOM)),
    NHOM_DA_DU_THANH_VIEN(PropertiesReader.getProperty(PropertyKeys.NHOM_DA_DU_THANH_VIEN)),
    GIANG_VIEN_HUONG_DAN_ALREADY_HAVE_ENOUGH_GROUPS(PropertiesReader.getProperty(PropertyKeys.GIANG_VIEN_HUONG_DAN_ALREADY_HAVE_ENOUGH_GROUPS)),
    SINH_VIEN_KHONG_CO_NHOM(PropertiesReader.getProperty(PropertyKeys.SINH_VIEN_KHONG_CO_NHOM)),
    ROI_NHOM_KHI_LA_TRUONG_NHOM(PropertiesReader.getProperty(PropertyKeys.ROI_NHOM_KHI_LA_TRUONG_NHOM)),
    SINH_VIEN_DANG_LA_TRUONG_NHOM(PropertiesReader.getProperty(PropertyKeys.SINH_VIEN_DANG_LA_TRUONG_NHOM)),
    TRUONG_NHOM_MOI_KHONG_CUNG_NHOM(PropertiesReader.getProperty(PropertyKeys.TRUONG_NHOM_MOI_KHONG_CUNG_NHOM)),
    INVALID_HAN_SINH_VIEN(PropertiesReader.getProperty(PropertyKeys.INVALID_HAN_SINH_VIEN)),
    INVALID_HAN_GIANG_VIEN (PropertiesReader.getProperty(PropertyKeys.INVALID_HAN_GIANG_VIEN)),
    INVALID_HAN_CHU_NHIEM_BO_MON(PropertiesReader.getProperty(PropertyKeys.INVALID_HAN_CHU_NHIEM_BO_MON)),
    INVALID_DATE(PropertiesReader.getProperty(PropertyKeys.INVALID_DATE)),
    INVALID_NGAY_KET_THUC(PropertiesReader.getProperty(PropertyKeys.INVALID_NGAY_KETTHUC));

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
