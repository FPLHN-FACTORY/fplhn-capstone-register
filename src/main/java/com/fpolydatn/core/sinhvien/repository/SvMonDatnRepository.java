package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.response.SvMonDatnResponse;
import com.fpolydatn.repository.MonDatnRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author SonPT
 */
public interface SvMonDatnRepository extends MonDatnRepository {

    @Query(value = "SELECT id,ma_mon,ten_mon_datn FROM mon_datn", nativeQuery = true)
    List<SvMonDatnResponse> findMonDatn();

    @Query(value = "SELECT nhom_mon_datn_id FROM do_an_tot_nghiep.mon_datn WHERE id = :id", nativeQuery = true)
    String getNhomMonDatnIdByMonChuongTrinhId(@Param("id") String id);

    @Query(value = "SELECT id,ma_mon,ten_mon_datn FROM mon_datn WHERE nhom_mon_datn_id = :id", nativeQuery = true)
    List<SvMonDatnResponse> getMonDatnTuongDuong(@Param("id") String id);
}
