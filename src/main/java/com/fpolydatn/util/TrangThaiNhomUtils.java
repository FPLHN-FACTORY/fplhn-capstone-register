package com.fpolydatn.util;

/**
 * @author thangncph26123
 */
public class TrangThaiNhomUtils {

    public static String ConvertToTrangThai(int trangThaiNhom) {
        String trangThaiNhomStr = "";
        switch (trangThaiNhom) {
            case 0:
                trangThaiNhomStr = "Mới thành lập";
                break;
            case 1:
                trangThaiNhomStr = "Đã liên hệ giảng viên";
                break;
            case 2:
                trangThaiNhomStr = "Sẵn sàng phê duyệt";
                break;
            case 3:
                trangThaiNhomStr = "Cần sửa";
                break;
            case 4:
                trangThaiNhomStr = "Giảng viên đã chốt";
                break;
            case 5:
                trangThaiNhomStr = "Chủ nhiệm bộ môn đã chốt";
                break;
        }
        return trangThaiNhomStr;
    }

}
