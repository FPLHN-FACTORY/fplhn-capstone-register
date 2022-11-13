package com.fpolydatn.util;

import com.fpolydatn.infrastructure.constant.TrangThaiNhom;

/**
 * @author thangncph26123
 */
public class TrangThaiNhomConverter {

    public static String ConvertToTrangThai(TrangThaiNhom trangThaiNhom) {
        String trangThaiNhomStr = "";
        switch (trangThaiNhom) {
            case MOI_THANH_LAP:
                trangThaiNhomStr = "Mới thành lập";
                break;
            case CHU_NHIEM_DA_XAC_NHAN:
                trangThaiNhomStr = "Chủ nhiệm bộ môn đã xác nhận";
                break;
            case GIANG_VIEN_DA_LIEN_HE:
                trangThaiNhomStr = "Giảng viên đã liên hệ";
                break;
            case CAN_SUA:
                trangThaiNhomStr = "Cần sửa";
                break;
            case GVHD_CHOT:
                trangThaiNhomStr = "Giảng viên đã chốt";
                break;
            case CNBM_CHOT:
                trangThaiNhomStr = "Chủ nhiệm bộ môn đã chốt";
                break;
        }
        return trangThaiNhomStr;
    }

}
