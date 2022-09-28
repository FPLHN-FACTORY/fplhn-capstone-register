package com.fpolydatn.util;

import com.fpolydatn.infrastructure.constant.TrangThaiNhom;

/**
 * @author thangncph26123
 */
public class TrangThaiNhomUtils {

    public static String ConvertToTrangThai(TrangThaiNhom trangThaiNhom) {
        String trangThaiNhomStr = "";
        switch (trangThaiNhom) {
            case MOI_THANH_LAP:
                trangThaiNhomStr = "Mới thành lập";
                break;
            case DA_LIEN_HE_GV:
                trangThaiNhomStr = "Đã liên hệ giảng viên";
                break;
            case SAN_SANG_PHE_DUYET:
                trangThaiNhomStr = "Sẵn sàng phê duyệt";
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
