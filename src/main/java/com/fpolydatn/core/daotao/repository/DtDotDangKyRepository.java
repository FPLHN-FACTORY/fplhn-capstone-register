package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindDotDangKyRequest;
import com.fpolydatn.core.daotao.model.response.DtDetailDotDangKyResponse;
import com.fpolydatn.core.daotao.model.response.DtDotDangKyResponse;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface DtDotDangKyRepository extends DotDangKyRepository {

    @Query(value = """
            WITH SoNhomKhongDuDK AS (SELECT n.id, n.dot_dang_ky_id
                    FROM nhom_datn n JOIN sinh_vien sv ON n.id = sv.nhom_id
                    WHERE n.co_so_id = :#{#req.coSoId}
                    GROUP BY n.id HAVING COUNT(sv.id) < 4),
                SoNhomKhongDuDKTheoDot AS (SELECT COUNT(snkddk.id) AS so_nhom, snkddk.dot_dang_ky_id 
                FROM SoNhomKhongDuDK snkddk GROUP BY snkddk.dot_dang_ky_id)
            SELECT ROW_NUMBER() OVER(ORDER BY ddk.last_modified_date DESC) AS STT, ddk.id, hk.ten_hoc_ky,
            ddk.ten_dot_dang_ky, ddk.ngay_bat_dau, ddk.ngay_ket_thuc,
            (ddk.ngay_ket_thuc > :curDate AND ddk.ngay_bat_dau < :curDate) AS trang_thai,
            (SELECT COUNT(id) FROM sinh_vien WHERE dot_dang_ky_id = ddk.id) AS tong_sinh_vien,
            (SELECT COUNT(id) FROM sinh_vien WHERE dot_dang_ky_id = ddk.id AND nhom_id IS NULL) AS tong_sv_chua_co_nhom,
            (SELECT count(id) FROM nhom_datn WHERE dot_dang_ky_id = ddk.id) AS tong_so_nhom,
            COALESCE(snkddktd.so_nhom, 0) AS tong_nhom_khong_hop_le           
            FROM dot_dang_ky ddk
            INNER JOIN hoc_ky hk ON ddk.hoc_ky_id = hk.id
            LEFT JOIN SoNhomKhongDuDKTheoDot snkddktd ON ddk.id = snkddktd.dot_dang_ky_id
            WHERE ddk.co_so_id = :#{#req.coSoId}
            GROUP BY ddk.id ORDER BY ddk.last_modified_date DESC
            """,
            countQuery = """
                    SELECT COUNT(ddk.id) 
                    FROM dot_dang_ky ddk
                    INNER JOIN hoc_ky hk ON ddk.hoc_ky_id = hk.id WHERE ddk.co_so_id = :#{#req.coSoId} 
                    GROUP BY ddk.id ORDER BY ddk.last_modified_date DESC
                    """,
            nativeQuery = true)
    Page<DtDotDangKyResponse> findByCoSo(@Param("req") DtFindDotDangKyRequest req,
                                         @Param("curDate") Long currentDate,
                                         Pageable page);

    @Query(value = "SELECT id, created_date, last_modified_date, co_so_id, hoc_ky_id, " +
            "ngay_bat_dau, ngay_ket_thuc, ten_dot_dang_ky, han_sinh_vien, han_giang_vien, han_chu_nhiem_bo_mon " +
            "FROM dot_dang_ky WHERE co_so_id = :coSoId"
            , nativeQuery = true)
    List<DotDangKy> getAllByCoSo(@Param("coSoId") String coSoId);

    @Query(value = """
            SELECT * FROM do_an_tot_nghiep.dot_dang_ky ddk
            WHERE ddk.ten_dot_dang_ky = :tenDotDangKy
            """, nativeQuery = true)
    DotDangKy findName(@Param("tenDotDangKy") String tenDotDangKy);

    @Query(value = """
            SELECT ddk.id, hk.ten_hoc_ky,
            ddk.ten_dot_dang_ky, ddk.ngay_bat_dau, ddk.ngay_ket_thuc, ddk.han_sinh_vien, ddk.han_giang_vien,
            ddk.han_chu_nhiem_bo_mon, COUNT(sv.dot_dang_ky_id) AS tong_sinh_vien,
            COUNT(sv.id) - COUNT(sv.nhom_id) AS tong_sinh_vien_chua_co_nhom,
            COUNT(sv.nhom_id) AS tong_sinh_vien_co_nhom,
            (SELECT COUNT(id) FROM nhom_datn WHERE co_so_id = :coSoId AND dot_dang_ky_id = :id) AS tong_so_nhom,
            ROUND((SELECT COUNT(id) FROM nhom_datn 
            	WHERE co_so_id = :coSoId AND dot_dang_ky_id = :id AND trang_thai != 1 AND trang_thai != 0) / 
            (SELECT COUNT(id) FROM nhom_datn 
            	WHERE co_so_id = :coSoId AND dot_dang_ky_id = :id) * 100, 1) AS ti_le_xac_nhan,
            (ddk.ngay_ket_thuc > :curDate AND ddk.ngay_bat_dau < :curDate) AS trang_thai,
            (SELECT COUNT(*) FROM (SELECT COUNT(n.id) FROM nhom_datn n INNER JOIN sinh_vien sv ON n.id = sv.nhom_id 
            	WHERE n.co_so_id = :coSoId AND n.dot_dang_ky_id = :id 
            	GROUP BY n.id HAVING COUNT(sv.id) > 3) AS nhom_hop_le) AS so_nhom_hop_le
            FROM dot_dang_ky ddk LEFT JOIN sinh_vien sv ON ddk.id = sv.dot_dang_ky_id
            INNER JOIN hoc_ky hk ON ddk.hoc_ky_id = hk.id  WHERE ddk.co_so_id = :coSoId AND ddk.id = :id
            GROUP BY ddk.id ORDER BY ddk.last_modified_date DESC
            """,
            countQuery = """
                    SELECT COUNT(ddk.id) 
                    FROM dot_dang_ky ddk LEFT JOIN sinh_vien sv ON ddk.id = sv.dot_dang_ky_id 
                    INNER JOIN hoc_ky hk ON ddk.hoc_ky_id = hk.id WHERE ddk.co_so_id = :coSoId AND ddk.id = :id
                    GROUP BY ddk.id ORDER BY ddk.last_modified_date DESC
                    """,
            nativeQuery = true)
    DtDetailDotDangKyResponse detailDotDangKy(@Param("id") String id,
                                              @Param("curDate") Long currentDate,
                                              @Param("coSoId") String coSoId);

}
