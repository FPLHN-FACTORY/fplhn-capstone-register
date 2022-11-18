package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.MonDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author hungpv
 */
public interface DtMonDatnRepository extends MonDatnRepository {
    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) AS stt, m.id, m.ma_mon, m.ten_mon_datn, cn.ten_chuyen_nganh,
             (SELECT CONCAT(b.ten_mon_datn, ' - ', b.ma_mon) FROM mon_datn b where b.id = m.nhom_mon_datn_id) as ten_nhom_mon
                FROM  mon_datn m
                LEFT JOIN chuyen_nganh cn ON m.chuyen_nganh_id = cn.id 
                WHERE m.co_so_id = :#{#req.coSoId} AND nhom_mon_datn_id IS NOT NULL
                ORDER BY m.last_modified_date DESC 
            """, countQuery = """
            SELECT COUNT(m.id)
                FROM  mon_datn m
                LEFT JOIN chuyen_nganh cn ON m.chuyen_nganh_id = cn.id 
                WHERE m.co_so_id = :#{#req.coSoId} AND nhom_mon_datn_id IS NOT NULL
                ORDER BY m.last_modified_date DESC 
            """, nativeQuery = true)
    Page<DtMonDatnResponse> getAllByCoSo(@Param("req") DtFindMonDatnRequest req, Pageable page);

    @Query(value = """
            SELECT m.id, 
                m.created_date, 
                m.last_modified_date, 
                m.co_so_id, 
                m.chuyen_nganh_id, 
                m.ma_mon, 
                m.nhom_mon_datn_id, 
                m.ten_mon_datn
                FROM mon_datn m WHERE m.ma_mon = :maMon AND m.co_so_id = :coSoId
            """, nativeQuery = true)
    MonDatn findByMaMon(@Param("maMon") String maMon, @Param("coSoId") String coSoId);

    @Query(value = """
                SELECT 
                 m.id,
                 m.ma_mon AS 'name'
                 FROM do_an_tot_nghiep.mon_datn m
                 WHERE m.co_so_id = :coSoId
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllMaMon(@Param("coSoId") String coSoId);

    @Query(value = """ 
                SELECT m.id, 
                m.created_date, 
                m.last_modified_date, 
                m.co_so_id, 
                m.chuyen_nganh_id, 
                m.ma_mon, 
                m.nhom_mon_datn_id, 
                m.ten_mon_datn
                FROM mon_datn m 
                WHERE nhom_mon_datn_id IS NULL AND co_so_id = :coSoId AND chuyen_nganh_id = :chuyeNganhId
            """, nativeQuery = true)
    List<MonDatn> getNhomMonDatnByCoSo(@Param("coSoId") String coSoId, @Param("chuyeNganhId") String chuyeNganhId);

    @Query(value = """
                SELECT COUNT(giang_vien_id)
                FROM phan_cong_huong_dan
                WHERE mon_datn_id = :id                                                       
            """, nativeQuery = true)
    Integer countInPhanCongHuongDan(@Param("id") String id);

    @Query(value = """
                SELECT COUNT(id)
                FROM sinh_vien
                WHERE mon_datn_id = :id AND co_so_id = :coSoId                                                
            """, nativeQuery = true)
    Integer countInSinhVien(@Param("id") String id, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT m.id, 
                m.created_date, 
                m.last_modified_date, 
                m.co_so_id, 
                m.chuyen_nganh_id, 
                m.ma_mon, 
                m.nhom_mon_datn_id, 
                m.ten_mon_datn
                FROM mon_datn m 
                WHERE m.nhom_mon_datn_id IS NULL 
                AND m.co_so_id = :coSoId 
            """, nativeQuery = true)
    List<MonDatn> getListNhomMonDatn(@Param("coSoId") String coSoId);

    @Query(value = """
            SELECT m.id, 
                m.created_date, 
                m.last_modified_date, 
                m.co_so_id, 
                m.chuyen_nganh_id, 
                m.ma_mon, 
                m.nhom_mon_datn_id, 
                m.ten_mon_datn
                FROM mon_datn m 
                WHERE m.co_so_id = :coSoId 
                AND m.nhom_mon_datn_id IS NOT NULL 
            """, nativeQuery = true)
    List<MonDatn> getListMonDatnByIdCoSo(@Param("coSoId") String coSoId);
}
