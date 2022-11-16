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
    CAN_BO_EXSIT(PropertiesReader.getProperty(PropertyKeys.CAN_BO_EXSIT)),
    NO_SPACES_ALLOW(PropertiesReader.getProperty(PropertyKeys.NO_SPACES_ALLOW)),
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
    INVALID_NGAY_KET_THUC(PropertiesReader.getProperty(PropertyKeys.INVALID_NGAY_KETTHUC)),
    THOI_GIAN_ROI_NHOM_CHUA_DU_12_TIENG(PropertiesReader.getProperty(PropertyKeys.THOI_GIAN_ROI_NHOM_CHUA_DU_12_TIENG)),
    HET_HAN_QUYEN_SINH_VIEN(PropertiesReader.getProperty(PropertyKeys.HET_HAN_QUYEN_SINH_VIEN)),
    GIANG_VIEN_DANG_CO_NHOM_HUONG_DAN(PropertiesReader.getProperty(PropertyKeys.GIANG_VIEN_DANG_CO_NHOM_HUONG_DAN)),
    KHONG_DUOC_XOA_MON(PropertiesReader.getProperty(PropertyKeys.KHONG_DUOC_XOA_MON)),
    CHUYEN_NGANH_KHONG_DUOC_XOA(PropertiesReader.getProperty(PropertyKeys.CHUYEN_NGANH_KHONG_DUOC_XOA)),
    MOC_THOI_GIAN_GVHD(PropertiesReader.getProperty(PropertyKeys.MOC_THOI_GIAN_GVHD)),
    DONT_UPDATE_TEN_DE_TAI(PropertiesReader.getProperty(PropertyKeys.DONT_UPDATE_TEN_DE_TAI)),
    DONT_PHE_DUYET_NHOM_DATN(PropertiesReader.getProperty(PropertyKeys.DONT_PHE_DUYET_NHOM_DATN)),
    DONT_CHOT_DE_TAI_NHOM_DATN(PropertiesReader.getProperty(PropertyKeys.DONT_CHOT_DE_TAI_NHOM_DATN)),
    VUOT_QUA_SO_LUONG_CHO_PHEP(PropertiesReader.getProperty(PropertyKeys.VUOT_QUA_SO_LUONG_CHO_PHEP)),
    MON_KHONG_TUONG_DUONG(PropertiesReader.getProperty(PropertyKeys.MON_KHONG_TUONG_DUONG)),
    GIANG_VIEN_KHONG_DAY_MON_NAY(PropertiesReader.getProperty(PropertyKeys.GIANG_VIEN_KHONG_DAY_MON_NAY)),
    CHUA_DEN_THOI_GIAN_CUA_CHU_NHIEM(PropertiesReader.getProperty(PropertyKeys.CHUA_DEN_THOI_GIAN_CUA_CHU_NHIEM)),
    MON_DATN_KHONG_THOA_MAN_VOI_SINH_VIEN(PropertiesReader.getProperty(PropertyKeys.MON_DATN_KHONG_THOA_MAN_VOI_SINH_VIEN)),
    CHUYEN_NGANH_ALREADY_EXIST(PropertiesReader.getProperty(PropertyKeys.CHUYEN_NGANH_ALREADY_EXIST)),
    CHU_NHIEM_ALREADY_EXIST(PropertiesReader.getProperty(PropertyKeys.CHU_NHIEM_ALREADY_EXIST)),
    MA_NHOM_MON_DATN_ALREADY_EXIST(PropertiesReader.getProperty(PropertyKeys.MA_NHOM_MON_DATN_ALREADY_EXIST)),
    NHOM_MON_DATN_NOT_EXIST(PropertiesReader.getProperty(PropertyKeys.NHOM_MON_DATN_NOT_EXIST)),

    GIANG_VIEN_DANG_DUOC_PHAN_CONG(PropertiesReader.getProperty(PropertyKeys.GIANG_VIEN_DANG_DUOC_PHAN_CONG)),
    NHOM_MON_DANG_TON_TAI_MON(PropertiesReader.getProperty(PropertyKeys.NHOM_MON_DANG_TON_TAI_MON)),
    TEN_TAI_KHOAN_CAN_BO_EXIST(PropertiesReader.getProperty(PropertyKeys.TEN_TAI_KHOAN_CAN_BO_EXIST)),
    CHU_NHIEM_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.CHU_NHIEM_NOT_EXISTS)),
    CHU_NHIEM_NAY_DANG_CHU_NHIEM_1_CHUYEN_NGANH(PropertiesReader.getProperty(PropertyKeys.CHU_NHIEM_NAY_DANG_CHU_NHIEM_1_CHUYEN_NGANH)),
    TAO_NHOM_THAT_BAI(PropertiesReader.getProperty(PropertyKeys.TAO_NHOM_THAT_BAI)),
    SINH_VIEN_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.SINH_VIEN_NOT_EXISTS)),
    SINH_VIEN_ALREADY_EXIST(PropertiesReader.getProperty(PropertyKeys.SINH_VIEN_ALREADY_EXIST));


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
