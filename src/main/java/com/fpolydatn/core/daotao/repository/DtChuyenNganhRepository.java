package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.core.daotao.model.request.DtFindChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.response.DtChuyenNganhResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author hungpv
 */

public interface DtChuyenNganhRepository extends ChuyenNganhRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY cn.last_modified_date DESC) AS stt,cn.id,ten_chuyen_nganh,cb.ten_tai_khoan,
            cb.ten_can_bo FROM chuyen_nganh cn LEFT JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id LEFT JOIN co_so cs ON 
            cn.co_so_id = cs.id WHERE cn.co_so_id = :#{#req.coSoId} AND
                    ( :#{#req.tenChuyenNganh} IS NULL 
                        OR :#{#req.tenChuyenNganh} LIKE '' 
                        OR ten_chuyen_nganh LIKE %:#{#req.tenChuyenNganh}% ) 
                ORDER BY cn.last_modified_date DESC 
            """, countQuery = """
            SELECT COUNT(cn.id)
                FROM chuyen_nganh cn LEFT JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id LEFT JOIN co_so cs ON 
            cn.co_so_id = cs.id  WHERE cn.co_so_id = :#{#req.coSoId} AND
                    ( :#{#req.tenChuyenNganh} IS NULL 
                        OR :#{#req.tenChuyenNganh} LIKE '' 
                        OR ten_chuyen_nganh LIKE %:#{#req.tenChuyenNganh}% ) 
                ORDER BY cn.last_modified_date DESC
            """, nativeQuery = true)
    Page<DtChuyenNganhResponse> findByName(@Param("req") DtFindChuyenNganhRequest req, Pageable page);

    @Query(value = """
                SELECT id, created_date, last_modified_date, co_so_id, chu_nhiem_bo_mon, ten_chuyen_nganh
                FROM chuyen_nganh WHERE co_so_id = :coSoId AND ten_chuyen_nganh = :tenChuyenNganh
            """, nativeQuery = true)
    ChuyenNganh findByTenChuyenNganh(@Param("tenChuyenNganh") String tenChuyenNganh, @Param("coSoId") String coSoId);

    @Query(value = """
                SELECT COUNT(id) FROM sinh_vien WHERE chuyen_nganh_id = :id AND co_so_id = :coSoId 
            """, nativeQuery = true)
    Integer countChuyenNganhInSinhVien(@Param("id") String id, @Param("coSoId") String coSoId);

    @Query(value = """
                SELECT COUNT(id) FROM mon_datn WHERE chuyen_nganh_id = :id AND co_so_id = :coSoId 
            """, nativeQuery = true)
    Integer countChuyeNganhInMonDatn(@Param("id") String id, @Param("coSoId") String coSoId);

    @Query(value = """
                SELECT cn.id FROM chuyen_nganh cn JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id WHERE 
                cn.co_so_id = :coSoId AND cn.ten_chuyen_nganh = :tenChuyenNganh AND cn.id <> :id 
            """, nativeQuery = true)
    String checkChuyenNganhByName(@Param("coSoId") String coSoId, @Param("tenChuyenNganh") String tenChuyenNganh,
                                  @Param("id") String id);

    @Query(value = """
                SELECT cb.id FROM chuyen_nganh cn JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id WHERE 
                cn.co_so_id = :coSoId AND cn.chu_nhiem_bo_mon = :chuNhiemBoMon AND cn.id <> :id 
            """, nativeQuery = true)
    String checkChuyenNganhByChuNhiem(@Param("coSoId") String coSoId, @Param("chuNhiemBoMon") String chuNhiemBoMon,
                          @Param("id") String id);

    @Query(value = """
                SELECT id FROM chuyen_nganh WHERE 
                co_so_id = :coSoId AND chu_nhiem_bo_mon = :chuNhiemBoMon
            """, nativeQuery = true)
    String checkChuyenNganhByCNBM(@Param("coSoId") String coSoId, @Param("chuNhiemBoMon") String chuNhiemBoMon);

    @Query(value = """
            SELECT * FROM chuyen_nganh cn WHERE cn.co_so_id = :coSoId
            """, nativeQuery = true)
    List<ChuyenNganh> getAllByIdCoSo(@Param("coSoId") String coSoId);

}
