package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtMonDatnSearchResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnExportResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnSinhVienExportExcelReponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author thepvph20110
 */
public interface DtSinhVienTheoDotRepository extends SinhVienRepository {

    @Query(value = """
            WITH CountSinhVien AS (
                SELECT y.id AS nhom_id, COUNT(x.id) AS so_thanh_vien
                FROM sinh_vien x
                JOIN nhom_datn y ON x.nhom_id = y.id
                WHERE x.dot_dang_ky_id = :#{#req.idDotDangKy} AND x.co_so_id = :#{#req.coSoId}
                GROUP BY y.id
            )
            SELECT ROW_NUMBER() OVER(
            ORDER BY CONVERT(SUBSTRING(s.ma_sinh_vien, 3), SIGNED) ASC
            ) AS stt, s.id, s.ma_sinh_vien, s.ten_sinh_vien, s.so_dien_thoai,
            s.email, s.khoa, c.id as 'chuyen_nganh_id', c.ten_chuyen_nganh,
            m.ma_mon, m.ten_mon_datn, n.ma_nhom, n.ten_de_tai, g.ten_gvhd ,
            d.id as 'id_dot_dang_ky', d.ten_dot_dang_ky, s.trang_thai,
            COALESCE(sv_count.so_thanh_vien, 0) AS so_thanh_vien,
            mct.id as 'id_mom_chuong_trinh',
            mct.ten_mon_datn as 'ten_mon_chuong_trinh',
            mct.ma_mon AS 'ma_mon_chuong_trinh'
            FROM do_an_tot_nghiep.sinh_vien s
            LEFT JOIN do_an_tot_nghiep.nhom_datn n ON n.id = s.nhom_id
            LEFT JOIN do_an_tot_nghiep.chuyen_nganh c ON c.id= s.chuyen_nganh_id
            LEFT JOIN do_an_tot_nghiep.mon_datn m ON m.id = s.mon_datn_id
            LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan g ON g.id = n.giang_vien_id
            LEFT JOIN do_an_tot_nghiep.dot_dang_ky d ON d.id =s.dot_dang_ky_id
            LEFT JOIN do_an_tot_nghiep.mon_datn mct ON mct.id = s.mon_chuong_trinh_id
            LEFT JOIN CountSinhVien sv_count ON n.id = sv_count.nhom_id
            WHERE (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL) AND 
            (s.chuyen_nganh_id = :#{#req.idChuyenNganh} OR  :#{#req.idChuyenNganh} IS NULL OR :#{#req.idChuyenNganh} LIKE '')
            AND (if('b'= :#{#req.idTrangThaiNhom}, s.nhom_id IS NULL,if('a'= :#{#req.idTrangThaiNhom}, s.nhom_id IS NOT NULL, (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL))))
            AND d.id =:#{#req.idDotDangKy} AND s.co_so_id = :#{#req.coSoId}
            GROUP BY s.id, sv_count.so_thanh_vien
            ORDER BY
              CASE WHEN :#{#req.sortNhomDatn} = 'lastModifiedDesc' THEN s.last_modified_date END DESC,
              CASE WHEN :#{#req.sortNhomDatn} = 'lastModifiedAsc' THEN s.last_modified_date END ASC,
              CASE WHEN :#{#req.sortNhomDatn} = 'maSinhVienAsc' THEN CONVERT(SUBSTRING(s.ma_sinh_vien, 3), SIGNED) END ASC,
              CASE WHEN :#{#req.sortNhomDatn} = 'maSinhVienDesc' THEN CONVERT(SUBSTRING(s.ma_sinh_vien, 3) ,SIGNED) END DESC,
              CASE WHEN :#{#req.sortNhomDatn} = 'chuyenNganhAsc' THEN c.ten_chuyen_nganh END ASC,
              CASE WHEN :#{#req.sortNhomDatn} = 'chuyenNganhDesc' THEN c.ten_chuyen_nganh END DESC,
              CASE WHEN :#{#req.sortNhomDatn} = 'gvhdAsc' THEN g.ten_gvhd END ASC,
              CASE WHEN :#{#req.sortNhomDatn} = 'gvhdDesc' THEN g.ten_gvhd END DESC 
            """,
            countQuery = """
                    WITH CountSinhVien AS (
                    SELECT y.id AS nhom_id, COUNT(x.id) AS so_thanh_vien
                    FROM sinh_vien x
                    JOIN nhom_datn y ON x.nhom_id = y.id
                    WHERE x.dot_dang_ky_id = :#{#req.idDotDangKy} AND x.co_so_id = :#{#req.coSoId}
                    GROUP BY y.id)
                    SELECT COUNT(s.id) 
                    FROM do_an_tot_nghiep.sinh_vien s
                    LEFT JOIN do_an_tot_nghiep.nhom_datn n ON n.id = s.nhom_id
                    LEFT JOIN do_an_tot_nghiep.chuyen_nganh c ON c.id= s.chuyen_nganh_id
                    LEFT JOIN do_an_tot_nghiep.mon_datn m ON m.id = s.mon_datn_id
                    LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan g ON g.id = n.giang_vien_id
                    LEFT JOIN do_an_tot_nghiep.dot_dang_ky d ON d.id =s.dot_dang_ky_id
                    LEFT JOIN do_an_tot_nghiep.mon_datn mct ON mct.id = s.mon_chuong_trinh_id
                    LEFT JOIN CountSinhVien sv_count ON n.id = sv_count.nhom_id
                    WHERE (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL) AND 
                    (s.chuyen_nganh_id = :#{#req.idChuyenNganh} OR  :#{#req.idChuyenNganh} IS NULL OR :#{#req.idChuyenNganh} LIKE '')
                    AND (if('b'= :#{#req.idTrangThaiNhom}, s.nhom_id IS NULL,if('a'= :#{#req.idTrangThaiNhom}, s.nhom_id IS NOT NULL, (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL))))
                    AND  d.id =:#{#req.idDotDangKy} AND s.co_so_id = :#{#req.coSoId}
                    GROUP BY s.id, sv_count.so_thanh_vien
                    ORDER BY
                      CASE WHEN :#{#req.sortNhomDatn} = 'lastModifiedDesc' THEN s.last_modified_date END DESC,
                      CASE WHEN :#{#req.sortNhomDatn} = 'lastModifiedAsc' THEN s.last_modified_date END ASC,
                      CASE WHEN :#{#req.sortNhomDatn} = 'maSinhVienAsc' THEN CONVERT(SUBSTRING(s.ma_sinh_vien, 3), SIGNED) END ASC,
                      CASE WHEN :#{#req.sortNhomDatn} = 'maSinhVienDesc' THEN CONVERT(SUBSTRING(s.ma_sinh_vien, 3) ,SIGNED) END DESC,
                      CASE WHEN :#{#req.sortNhomDatn} = 'chuyenNganhAsc' THEN c.ten_chuyen_nganh END ASC,
                      CASE WHEN :#{#req.sortNhomDatn} = 'chuyenNganhDesc' THEN c.ten_chuyen_nganh END DESC,
                      CASE WHEN :#{#req.sortNhomDatn} = 'gvhdAsc' THEN g.ten_gvhd END ASC,
                      CASE WHEN :#{#req.sortNhomDatn} = 'gvhdDesc' THEN g.ten_gvhd END DESC 
                            """, nativeQuery = true)
    Page<DtSinhVienTheoDotResponse> searchStudent(@Param("req") FindDtSinhVienTheoDotRequest req, Pageable page);

    @Query(value = """
                    SELECT 
                    s.id,
                    s.ma_sinh_vien AS 'name'
                    FROM do_an_tot_nghiep.sinh_vien s
                    WHERE s.co_so_id = :coSoId AND dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllMaSV(@Param("coSoId") String coSoId, @Param("dotDangKyId") String dotDangKyId);

    @Query(value = "SELECT * FROM sinh_vien WHERE dot_dang_ky_id = :dotDangKyId AND co_so_id = :coSoId", nativeQuery = true)
    List<SinhVien> findAllSinhVienByDotDangKyId(@Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY s.last_modified_date DESC) AS stt,
            s.id, s.ma_sinh_vien, s.ten_sinh_vien, s.so_dien_thoai, s.email,
            s.khoa, c.id as 'chuyen_nganh_id', c.ten_chuyen_nganh, m.ma_mon,
            m.ten_mon_datn, n.ma_nhom, n.ten_de_tai, g.ten_gvhd, d.id as 'id_dot_dang_ky',
            d.ten_dot_dang_ky, s.trang_thai,
            (SELECT COUNT(a.id) FROM sinh_vien a JOIN nhom_datn b ON a.nhom_id = b.id WHERE b.id = n.id) AS so_thanh_vien,
            mct.id as 'id_mom_chuong_trinh',
            mct.ten_mon_datn as 'ten_mon_chuong_trinh',
            mct.ma_mon AS 'ma_mon_chuong_trinh'
            FROM do_an_tot_nghiep.sinh_vien s
            LEFT JOIN do_an_tot_nghiep.nhom_datn n ON n.id = s.nhom_id
            LEFT JOIN do_an_tot_nghiep.chuyen_nganh c ON c.id= s.chuyen_nganh_id
            LEFT JOIN do_an_tot_nghiep.mon_datn m ON m.id = s.mon_datn_id
            LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan g ON g.id = n.giang_vien_id
            LEFT JOIN do_an_tot_nghiep.dot_dang_ky d ON d.id =s.dot_dang_ky_id
            LEFT JOIN do_an_tot_nghiep.mon_datn mct ON mct.id = s.mon_chuong_trinh_id
            WHERE s.id = :id
            """, nativeQuery = true)
    DtSinhVienTheoDotResponse searchStudentById(@Param("id") String id);

    @Query(value = """
            WITH CountSinhVien AS (
                SELECT y.id AS nhom_id, COUNT(x.id) AS so_thanh_vien
                FROM sinh_vien x
                JOIN nhom_datn y ON x.nhom_id = y.id
                WHERE x.dot_dang_ky_id = :#{#req.idDotDangKy} AND x.co_so_id = :#{#req.coSoId}
                GROUP BY y.id
            )
            SELECT ROW_NUMBER() OVER(
            ORDER BY CONVERT(SUBSTRING(s.ma_sinh_vien, 3), SIGNED) ASC
            ) AS stt, s.id, s.ma_sinh_vien, s.ten_sinh_vien, s.so_dien_thoai, s.email,
            s.khoa, c.ten_chuyen_nganh, m.ma_mon, m.ten_mon_datn, n.ma_nhom, n.ten_de_tai,
            g.ten_gvhd, d.ten_dot_dang_ky, s.trang_thai,
            COALESCE(sv_count.so_thanh_vien, 0) AS so_thanh_vien,
            mct.ten_mon_datn AS 'ten_mon_chuong_trinh',
            mct.ma_mon AS 'ma_mon_chuong_trinh'
            FROM do_an_tot_nghiep.sinh_vien s LEFT JOIN do_an_tot_nghiep.nhom_datn n ON n.id = s.nhom_id
            LEFT JOIN do_an_tot_nghiep.chuyen_nganh c ON c.id= s.chuyen_nganh_id
            LEFT JOIN do_an_tot_nghiep.mon_datn m ON m.id = s.mon_datn_id
            LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan g ON g.id = n.giang_vien_id
            LEFT JOIN do_an_tot_nghiep.dot_dang_ky d ON d.id =s.dot_dang_ky_id
            LEFT JOIN do_an_tot_nghiep.mon_datn mct ON mct.id = s.mon_chuong_trinh_id
            LEFT JOIN CountSinhVien sv_count ON n.id = sv_count.nhom_id
            WHERE (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL) AND 
            (s.chuyen_nganh_id = :#{#req.idChuyenNganh} OR  :#{#req.idChuyenNganh} IS NULL OR :#{#req.idChuyenNganh} LIKE '')
            AND (if('b'= :#{#req.idTrangThaiNhom}, s.nhom_id IS NULL,if('a'= :#{#req.idTrangThaiNhom}, s.nhom_id IS NOT NULL, (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL))))
            AND d.id =:#{#req.idDotDangKy} AND s.co_so_id = :#{#req.coSoId}
            GROUP BY s.id, sv_count.so_thanh_vien
            ORDER BY
              CASE WHEN :#{#req.sortNhomDatn} = 'lastModifiedDesc' THEN s.last_modified_date END DESC,
              CASE WHEN :#{#req.sortNhomDatn} = 'lastModifiedAsc' THEN s.last_modified_date END ASC,
              CASE WHEN :#{#req.sortNhomDatn} = 'maSinhVienAsc' THEN CONVERT(SUBSTRING(s.ma_sinh_vien, 3), SIGNED) END ASC,
              CASE WHEN :#{#req.sortNhomDatn} = 'maSinhVienDesc' THEN CONVERT(SUBSTRING(s.ma_sinh_vien, 3) ,SIGNED) END DESC,
              CASE WHEN :#{#req.sortNhomDatn} = 'chuyenNganhAsc' THEN c.ten_chuyen_nganh END ASC,
              CASE WHEN :#{#req.sortNhomDatn} = 'chuyenNganhDesc' THEN c.ten_chuyen_nganh END DESC,
              CASE WHEN :#{#req.sortNhomDatn} = 'gvhdAsc' THEN g.ten_gvhd END ASC,
              CASE WHEN :#{#req.sortNhomDatn} = 'gvhdDesc' THEN g.ten_gvhd END DESC 
            """, nativeQuery = true)
    List<DtSinhVienTheoDotResponse> exportExcel(@Param("req") FindDtSinhVienTheoDotRequest req);

    @Query(value = """
            SELECT * FROM do_an_tot_nghiep.sinh_vien s 
            WHERE s.nhom_id= :nhomId
            AND s.id <> :idNhomTruong 
            AND s.co_so_id = :coSoId
            AND s.dot_dang_ky_id = :dotDangKyId LIMIT 1
            """, nativeQuery = true)
    SinhVien motSVTrongNhom(@Param("idNhomTruong") String idNhomTruong,
                            @Param("nhomId") String nhomId,
                            @Param("coSoId") String coSoId,
                            @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
                SELECT id 
                FROM sinh_vien
                WHERE ma_sinh_vien = :maSinhVien 
                AND id <> :id
                AND co_so_id = :coSoId 
                AND dot_dang_ky_id = :dotDangKyId 
            """, nativeQuery = true)
    String findSinhVienByMaSVUpdate(@Param("maSinhVien") String maSinhVien,
                                    @Param("id") String id,
                                    @Param("coSoId") String coSoId,
                                    @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
                SELECT id 
                FROM sinh_vien
                WHERE email = :email 
                AND id <> :id
                AND co_so_id = :coSoId 
                AND dot_dang_ky_id = :dotDangKyId 
            """, nativeQuery = true)
    String findSinhVienByEmailUpdate(@Param("email") String email,
                                     @Param("id") String id,
                                     @Param("coSoId") String coSoId,
                                     @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
                SELECT id 
                FROM sinh_vien
                WHERE ma_sinh_vien = :maSinhVien 
                AND co_so_id = :coSoId 
                AND dot_dang_ky_id = :dotDangKyId 
            """, nativeQuery = true)
    String findSinhVienByMaSV(@Param("maSinhVien") String maSinhVien, @Param("coSoId") String coSoId,
                              @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
                SELECT id 
                FROM sinh_vien
                WHERE email = :email 
                AND co_so_id = :coSoId 
                AND dot_dang_ky_id = :dotDangKyId 
            """, nativeQuery = true)
    String findSinhVienByEmail(@Param("email") String email, @Param("coSoId") String coSoId,
                               @Param("dotDangKyId") String dotDangKyId);

    @Query(value = """
            SELECT id, ma_mon, ten_mon_datn FROM mon_datn 
            WHERE chuyen_nganh_id = :chuyenNganhId 
            AND co_so_id = :coSoId AND nhom_mon_datn_id IS NOT NULL
            """, nativeQuery = true)
    List<DtMonDatnSearchResponse> getAllMonDatnByChuyenNganhId(@Param("chuyenNganhId") String chuyenNganhId,
                                                               @Param("coSoId") String coSoId);

    @Query(value = """
        SELECT
        	n.id,
            n.ma_nhom,
            n.ten_nhom,
            COALESCE(n.ten_de_tai, 'Chưa có') AS ten_de_tai,
            COALESCE(gv.ten_gvhd, 'Chưa có') AS ten_gvhd,
            COALESCE(gv.ten_tai_khoan, 'Chưa có') AS ten_tai_khoan,
            n.trang_thai,
            n.truong_nhom_id
        FROM  do_an_tot_nghiep.nhom_datn n 
        LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan gv
             ON n.giang_vien_id = gv.id
        LEFT JOIN sinh_vien d ON d.nhom_id = n.id
        WHERE n.truong_nhom_id IS NOT NULL AND n.dot_dang_ky_id = :#{#req.dotDangKyId}
            AND n.co_so_id = :#{#req.coSoId}
            AND (:#{#req.maNhom} IS NULL OR :#{#req.maNhom} LIKE ''
            OR n.ma_nhom LIKE %:#{#req.maNhom}%) 
            AND (:#{#req.trangThai} IS NULL OR :#{#req.trangThai} LIKE ''
            OR n.trang_thai = CONVERT(:#{#req.trangThai} , UNSIGNED))
            GROUP BY n.id
            HAVING :#{#req.soThanhVien} IS NULL OR :#{#req.soThanhVien} LIKE '' OR COUNT(d.ma_sinh_vien) LIKE :#{#req.soThanhVien}
    """, nativeQuery = true)
    List<DtNhomDatnExportResponse> getNhomDatnExportExcel(@Param("req") final FindDtNhomDatnTheoDotRequest req);

    @Query(value = """
        SELECT
        	sv.id,
            sv.ma_sinh_vien,
            sv.ten_sinh_vien,
            cn.ten_chuyen_nganh,
            m.ten_mon_datn,
            sv.nhom_id,
            sv.email
        FROM do_an_tot_nghiep.sinh_vien sv JOIN do_an_tot_nghiep.chuyen_nganh cn
        ON sv.chuyen_nganh_id = cn.id
        JOIN do_an_tot_nghiep.mon_datn m
        ON sv.mon_datn_id = m.id
        LEFT JOIN nhom_datn nda ON nda.id = sv.nhom_id
        WHERE sv.nhom_id IS NOT NULL
         AND sv.dot_dang_ky_id = :#{#req.dotDangKyId}
             AND sv.co_so_id = :#{#req.coSoId}
             ORDER BY nda.ma_nhom
    """, nativeQuery = true)
    List<DtNhomDatnSinhVienExportExcelReponse> getSinhVienNhomDatnExportExcel(@Param("req") final FindDtNhomDatnTheoDotRequest req);

    @Query(value = "SELECT * FROM sinh_vien WHERE nhom_id = :idNhom AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    List<SinhVien> getAllSinhVien(@Param("idNhom") String idNhom, @Param("dotDangKyId") String dotDangKyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sinh_vien SET nhom_id = NULL , mon_datn_id  = NULL WHERE ma_sinh_vien = :maSinhVien AND dot_dang_ky_id = :dotDangKyId", nativeQuery = true)
    void deleteSinhVienOutGroup(@Param("maSinhVien") String maSinhVien, @Param("dotDangKyId") String dotDangKyId);

}



