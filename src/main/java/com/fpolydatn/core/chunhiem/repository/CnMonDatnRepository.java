package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.response.CnMonDatnResponse;
import com.fpolydatn.repository.MonDatnRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */

public interface CnMonDatnRepository extends MonDatnRepository {

    @Query(value = "SELECT nhom_mon_datn_id FROM mon_datn WHERE id = :idMaMon", nativeQuery = true)
    String findIdNhomMonDatn(@Param("idMaMon") String id);

    @Query(value = """
            SELECT nhom_mon_datn_id from mon_datn WHERE nhom_mon_datn_id = :idNhomMonDatn and id = :idMonChuongTrinh
            """, nativeQuery = true)
    String checkMonChuongTrinh(@Param("idNhomMonDatn") String idNhomMonDatn, @Param("idMonChuongTrinh") String idMonChuongTrinh);

    @Query(value = """
            SELECT id, ma_mon, ten_mon_datn AS ten_mon_datn FROM mon_datn 
            WHERE co_so_id = :coSoId AND nhom_mon_datn_id IS NULL
            """, nativeQuery = true)
    List<CnMonDatnResponse> getAllNhomMonDatn(@Param("coSoId") String coSoId);

    @Query(value = """
            SELECT id, ma_mon, ten_mon_datn AS ten_mon_datn FROM mon_datn 
            WHERE co_so_id = :coSoId AND nhom_mon_datn_id = :nhomMonId
            """, nativeQuery = true)
    List<CnMonDatnResponse> getAllMondatnByNhomMon(@Param("coSoId") String coSoId, @Param("nhomMonId") String nhomMonId);

    @Query(value = """
            SELECT a.id, ma_mon, ten_mon_datn AS ten_mon_datn 
            FROM mon_datn a LEFT JOIN chuyen_nganh b ON a.chuyen_nganh_id = b.id
            WHERE a.co_so_id = :coSoId AND b.chu_nhiem_bo_mon = :idChuNhiem 
            AND a.nhom_mon_datn_id IS NOT NULL
            """, nativeQuery = true)
    List<CnMonDatnResponse> getAllMonDatnByChuyenNganh(@Param("coSoId") String coSoId, @Param("idChuNhiem") String idChuNhiem);
}
