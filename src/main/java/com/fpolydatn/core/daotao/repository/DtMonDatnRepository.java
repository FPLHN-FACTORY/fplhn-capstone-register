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
            WITH danh_sach_gvhd_cte AS (
                SELECT
                    pc.mon_datn_id,
                    GROUP_CONCAT(gv.ten_tai_khoan SEPARATOR ', ') AS danh_sach_gvhd
                FROM phan_cong_huong_dan pc
                JOIN giang_vien_huong_dan gv ON pc.giang_vien_id = gv.id
                JOIN dot_dang_ky ddk ON gv.dot_dang_ky_id = ddk.id
                WHERE ddk.id = :#{#req.dotDangKyId}
                GROUP BY pc.mon_datn_id
            )
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) AS stt, 
            m.id, 
            m.ma_mon, 
            m.ten_mon_datn, 
            cn.ten_chuyen_nganh,
            COALESCE(dsg.danh_sach_gvhd, '') AS danh_sach_gvhd,
            cb.ten_can_bo,
            GROUP_CONCAT(DISTINCT CONCAT(m.ten_mon_datn, ' - ', m.ma_mon) SEPARATOR ', ') AS ten_nhom_mon,
            m.nhom_mon_datn_id
                FROM mon_datn m
                LEFT JOIN chuyen_nganh cn ON m.chuyen_nganh_id = cn.id 
                LEFT JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id
                LEFT JOIN danh_sach_gvhd_cte dsg ON m.id = dsg.mon_datn_id
                WHERE ( :#{#req.maMon} IS NULL 
                        OR :#{#req.maMon} LIKE '' 
                        OR ma_mon LIKE %:#{#req.maMon}% )  
                AND (m.chuyen_nganh_id = :#{#req.chuyenNganhId} 
                OR :#{#req.chuyenNganhId} IS NULL 
                OR :#{#req.chuyenNganhId} LIKE '')
                AND m.co_so_id = :#{#req.coSoId} 
                AND nhom_mon_datn_id IS NOT NULL
                GROUP BY m.id, m.ma_mon, m.ten_mon_datn, cn.ten_chuyen_nganh, m.nhom_mon_datn_id
                ORDER BY m.last_modified_date DESC 
            """, countQuery = """
            WITH danh_sach_gvhd_cte AS (
                SELECT
                    pc.mon_datn_id,
                    GROUP_CONCAT(gv.ten_tai_khoan SEPARATOR ', ') AS danh_sach_gvhd
                FROM phan_cong_huong_dan pc
                JOIN giang_vien_huong_dan gv ON pc.giang_vien_id = gv.id
                JOIN dot_dang_ky ddk ON gv.dot_dang_ky_id = ddk.id
                WHERE ddk.id = :#{#req.dotDangKyId}
                GROUP BY pc.mon_datn_id
            )
            SELECT COUNT(m.id)
                FROM mon_datn m
                LEFT JOIN chuyen_nganh cn ON m.chuyen_nganh_id = cn.id 
                LEFT JOIN can_bo cb ON cn.chu_nhiem_bo_mon = cb.id
                LEFT JOIN danh_sach_gvhd_cte dsg ON m.id = dsg.mon_datn_id
                WHERE ( :#{#req.maMon} IS NULL 
                        OR :#{#req.maMon} LIKE '' 
                        OR ma_mon LIKE %:#{#req.maMon}% )  
                AND (m.chuyen_nganh_id = :#{#req.chuyenNganhId} 
                OR :#{#req.chuyenNganhId} IS NULL 
                OR :#{#req.chuyenNganhId} LIKE '')
                AND m.co_so_id = :#{#req.coSoId} 
                AND nhom_mon_datn_id IS NOT NULL
                GROUP BY m.id, m.ma_mon, m.ten_mon_datn, cn.ten_chuyen_nganh, m.nhom_mon_datn_id
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
