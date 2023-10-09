package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.request.CnFindDashboardRequest;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.ChuyenNganhRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnChuyenNganhRepository extends ChuyenNganhRepository {

    @Query(value = """
            SELECT a.id, a.ten_chuyen_nganh AS name 
            FROM chuyen_nganh a WHERE a.co_so_id = :coSoId AND a.chu_nhiem_bo_mon = :idChuNhiem
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllChuyenNganhByIdChuNhiem(@Param("coSoId") String coSoId, @Param("idChuNhiem") String idChuNhiem);

    @Query(value = """
            SELECT a.id, CONCAT(a.ma_mon, ' - ', a.ten_mon_datn) AS name
            FROM mon_datn a JOIN chuyen_nganh b ON a.chuyen_nganh_id = b.id
            WHERE a.co_so_id = :coSoId AND b.chu_nhiem_bo_mon = :idChuNhiem
            AND a.nhom_mon_datn_id IS NOT NULL
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllMonDatnByIdChuNhiem(@Param("coSoId") String coSoId, @Param("idChuNhiem") String idChuNhiem);

    @Query(value = """
            SELECT a.* FROM chuyen_nganh a WHERE a.co_so_id = :coSoId 
            AND a.chu_nhiem_bo_mon = :idChuNhiem
            """, nativeQuery = true)
    List<ChuyenNganh> getAllChuyenNganhEntityByIdChuNhiem(@Param("coSoId") String coSoId, @Param("idChuNhiem") String idChuNhiem);

    @Query(value = """
            SELECT a.id FROM chuyen_nganh a WHERE a.co_so_id = :coSoId 
            AND a.chu_nhiem_bo_mon = :idChuNhiem
            """, nativeQuery = true)
    List<String> getAllIdChuyenNganhByIdChuNhiem(@Param("coSoId") String coSoId, @Param("idChuNhiem") String idChuNhiem);
}
