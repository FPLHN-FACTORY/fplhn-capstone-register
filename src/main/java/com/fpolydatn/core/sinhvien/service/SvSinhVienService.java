package com.fpolydatn.core.sinhvien.service;

/**
 * @author thangdt
 */
public interface SvSinhVienService {

    String getNhomIdById(String coSoId, String dotDangKyId, String id);

    Integer getSoThanhVienByNhomId(String nhomId);

    String getMonDatnIdBySinhVienId(String id);

    String getMonChuongTrinhIdById(String id);

}
