package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.chunhiem.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.NhomDatnResponse;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnNhomDatnRepository extends NhomDatnRepository {

    @Query(value = """
        SELECT b.id, ROW_NUMBER() OVER(ORDER BY b.last_modified_date DESC) AS stt, b.ten_nhom
        ,b.ten_de_tai , f.ten_chuyen_nganh,COUNT(d.ma_sinh_vien)
        AS total_ma_sinh_vien, c.ma_sinh_vien, e.ten_gvhd, b.trang_thai, b.last_modified_date
        FROM nhom_datn b JOIN sinh_vien c ON b.truong_nhom_id = c.id
        JOIN sinh_vien d ON d.nhom_id = b.id
        JOIN chuyen_nganh f ON f.id = d.chuyen_nganh_id
        JOIN giang_vien_huong_dan e ON e.id = b.giang_vien_id
        JOIN dot_dang_ky g ON g.id = b.dot_dang_ky_id
        JOIN can_bo h ON h.id = f.chu_nhiem_bo_mon
        WHERE (:#{#req.tenNhom} IS NULL OR :#{#req.tenNhom} LIKE ''
        OR b.ten_nhom LIKE %:#{#req.tenNhom}%) 
        AND (:#{#req.tenChuyenNganh} IS NULL OR :#{#req.tenChuyenNganh} LIKE ''
        OR f.ten_chuyen_nganh LIKE %:#{#req.tenChuyenNganh}%)
        AND (:#{#req.tenGvhd} IS NULL OR :#{#req.tenGvhd} LIKE ''
        OR e.ten_gvhd LIKE %:#{#req.tenGvhd}%)
        AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
        OR b.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
        AND b.co_so_id = '8087fd6a-86a7-4c20-ade3-02d45e9d1374'
        GROUP BY b.id ORDER BY b.last_modified_date DESC 
        """, nativeQuery = true)
    Page<NhomDatnResponse> findNhomDatn(@Param("req") FindNhomDatnRequest req, Pageable page);

    @Query(value = """
        SELECT COUNT(1) FROM nhom_datn hk WHERE ten_nhom LIKE %:tenHocKy%
        """, nativeQuery = true)
    Long countByName(@Param("tenHocKy") String tenHocKy);

    @Query(value = "SELECT ten_chuyen_nganh FROM chuyen_nganh WHERE co_so_id = '8087fd6a-86a7-4c20-ade3-02d45e9d1374'", nativeQuery = true)
    List<String> listTenChuyenNganh();

    @Query(value = "SELECT ten_gvhd FROM giang_vien_huong_dan WHERE co_so_id = '8087fd6a-86a7-4c20-ade3-02d45e9d1374'", nativeQuery = true)
    List<String> listTenGvhd();

}
