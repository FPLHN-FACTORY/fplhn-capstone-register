package com.fpolydatn.core.sinhvien.service;

/**
 * @author thangdt
 */
public interface SvSinhVienService {

    String getNhomIdById(String coSoId, String dotDangKyId, String id);

    Integer getSoThanhVienByNhomId(String nhomId, String coSoId, String dotDangKyId);

    String getMonDatnIdBySinhVienId(String id);

}
