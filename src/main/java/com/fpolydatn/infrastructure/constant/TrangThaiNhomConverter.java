package com.fpolydatn.infrastructure.constant;

import com.fpolydatn.infrastructure.constant.TrangThaiNhom;

import java.util.HashMap;
import java.util.Map;

/**
 * @author thangncph26123
 */
public final class TrangThaiNhomConverter {

    private TrangThaiNhomConverter(){}

    private static final Map<TrangThaiNhom, String> trangThaiMap = new HashMap<>();

    static {
        trangThaiMap.put(TrangThaiNhom.MOI_THANH_LAP, "Mới thành lập");
        trangThaiMap.put(TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN, "Chủ nhiệm bộ môn đã xác nhận");
        trangThaiMap.put(TrangThaiNhom.GIANG_VIEN_DA_LIEN_HE, "Giảng viên đã liên hệ");
        trangThaiMap.put(TrangThaiNhom.CAN_SUA, "Cần sửa");
        trangThaiMap.put(TrangThaiNhom.GVHD_CHOT, "Giảng viên đã chốt");
        trangThaiMap.put(TrangThaiNhom.CNBM_CHOT, "Chủ nhiệm bộ môn đã chốt");
    }

    public static String convertToTrangThai(TrangThaiNhom trangThaiNhom) {
        return trangThaiMap.getOrDefault(trangThaiNhom, "");
    }

}
