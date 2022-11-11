package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface DtSinhVienTheoDotRepository extends SinhVienRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY s.last_modified_date DESC) AS stt,
            s.id,
            s.ma_sinh_vien,
            s.ten_sinh_vien,
            s.so_dien_thoai,
            s.email,
            s.khoa,
            c.ten_chuyen_nganh,
            m.ma_mon,
            m.ten_mon_datn,
            n.ma_nhom,
            n.ten_de_tai,
            g.ten_gvhd ,
            d.ten_dot_dang_ky ,
            s.trang_thai,
            n.so_thanh_vien,
            mct.ten_mon_datn AS 'ten_mon_chuong_trinh',
            mct.ma_mon AS 'ma_mom_chuong_trinh'
            FROM do_an_tot_nghiep.sinh_vien s
            LEFT JOIN (select nda.*,COUNT(sv.ma_sinh_vien) AS so_thanh_vien from do_an_tot_nghiep.nhom_datn nda
            JOIN sinh_vien sv ON sv.nhom_id = nda.id) n
            ON n.id= s.nhom_id
            LEFT JOIN do_an_tot_nghiep.chuyen_nganh c ON c.id= s.chuyen_nganh_id
            LEFT JOIN do_an_tot_nghiep.mon_datn m ON m.id = s.mon_datn_id
            LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan g ON g.id = n.giang_vien_id
            LEFT JOIN do_an_tot_nghiep.dot_dang_ky d ON d.id =s.dot_dang_ky_id
            LEFT JOIN do_an_tot_nghiep.mon_datn mct ON mct.id = s.mon_chuong_trinh_id
            WHERE (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL) AND 
            (s.chuyen_nganh_id = :#{#req.idChuyenNganh} OR  :#{#req.idChuyenNganh} IS NULL OR :#{#req.idChuyenNganh} LIKE '')
            AND (if('b'= :#{#req.idTrangThaiNhom},nhom_id IS NULL,if('a'= :#{#req.idTrangThaiNhom},nhom_id IS NOT NULL, (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL))))
            AND d.id =:#{#req.idDotDangKy} AND s.co_so_id = :#{#req.coSoId}
            GROUP BY s.id 
            ORDER BY s.last_modified_date DESC """,
            countQuery = """
                     SELECT count(s.id) 
                    FROM do_an_tot_nghiep.sinh_vien s 
                    LEFT JOIN do_an_tot_nghiep.nhom_datn n ON n.id= s.nhom_id 
                    LEFT JOIN do_an_tot_nghiep.chuyen_nganh c ON c.id= s.chuyen_nganh_id 
                    LEFT JOIN do_an_tot_nghiep.mon_datn m ON m.id = s.mon_datn_id 
                    LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan g ON g.id = n.giang_vien_id 
                    LEFT JOIN do_an_tot_nghiep.dot_dang_ky d ON d.id =s.dot_dang_ky_id
                    LEFT JOIN do_an_tot_nghiep.mon_datn mct ON mct.id = s.mon_chuong_trinh_id
                    WHERE (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL) AND 
                    (s.chuyen_nganh_id = :#{#req.idChuyenNganh} OR  :#{#req.idChuyenNganh} IS NULL OR :#{#req.idChuyenNganh} LIKE '')
                    AND (if('b'= :#{#req.idTrangThaiNhom},nhom_id IS NULL,if('a'= :#{#req.idTrangThaiNhom},nhom_id IS NOT NULL, (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL))))
                    AND  d.id =:#{#req.idDotDangKy} AND s.co_so_id = :#{#req.coSoId}
                    GROUP BY s.id  
                    """, nativeQuery = true)
    Page<DtSinhVienTheoDotResponse> searchStudent(@Param("req") FindDtSinhVienTheoDotRequest req, Pageable page);

    @Query(value = """
                SELECT 
                s.id,
                s.ma_sinh_vien AS 'name'
                 FROM do_an_tot_nghiep.sinh_vien s
                 WHERE s.co_so_id = :coSoId
            """, nativeQuery = true)
    List<SimpleEntityProj> getAllMaSV(@Param("coSoId") String coSoId);

    @Query(value = "SELECT * FROM sinh_vien WHERE dot_dang_ky_id = :dotDangKyId AND co_so_id = :coSoId", nativeQuery = true)
    List<SinhVien> findAllSinhVienByDotDangKyId(@Param("dotDangKyId") String dotDangKyId, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY s.last_modified_date DESC) AS stt,
                        s.id,
                        s.ma_sinh_vien,
                        s.ten_sinh_vien,
                        s.so_dien_thoai,
                        s.email,
                        s.khoa,
                        c.ten_chuyen_nganh,
                        m.ma_mon,
                        m.ten_mon_datn,
                        n.ma_nhom,
                        n.ten_de_tai,
                        g.ten_gvhd ,
                        d.ten_dot_dang_ky ,
                        s.trang_thai,
                        n.so_thanh_vien,
                        mct.ten_mon_datn AS 'ten_mon_chuong_trinh',
                        mct.ma_mon AS 'ma_mom_chuong_trinh'
                        FROM do_an_tot_nghiep.sinh_vien s
                        LEFT JOIN (select nda.*,COUNT(sv.ma_sinh_vien) AS so_thanh_vien from do_an_tot_nghiep.nhom_datn nda
                        JOIN sinh_vien sv ON sv.nhom_id = nda.id) n
                        ON n.id= s.nhom_id
                        LEFT JOIN do_an_tot_nghiep.chuyen_nganh c ON c.id= s.chuyen_nganh_id
                        LEFT JOIN do_an_tot_nghiep.mon_datn m ON m.id = s.mon_datn_id
                        LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan g ON g.id = n.giang_vien_id
                        LEFT JOIN do_an_tot_nghiep.dot_dang_ky d ON d.id =s.dot_dang_ky_id
            			LEFT JOIN do_an_tot_nghiep.mon_datn mct ON mct.id = s.mon_chuong_trinh_id
                        WHERE s.id = :id and s.co_so_id = :coSoId
            """, nativeQuery = true)
    DtSinhVienTheoDotResponse searchStudentById(@Param("id") String id, @Param("coSoId") String coSoId);


    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY s.last_modified_date DESC) AS stt,
            s.id,
            s.ma_sinh_vien,
            s.ten_sinh_vien,
            s.so_dien_thoai,
            s.email,
            s.khoa,
            c.ten_chuyen_nganh,
            m.ma_mon,
            m.ten_mon_datn,
            n.ma_nhom,
            n.ten_de_tai,
            g.ten_gvhd ,
            d.ten_dot_dang_ky ,
            s.trang_thai,
            n.so_thanh_vien,
            mct.ten_mon_datn AS 'ten_mon_chuong_trinh',
            mct.ma_mon AS 'ma_mom_chuong_trinh'
            FROM do_an_tot_nghiep.sinh_vien s
            LEFT JOIN (select nda.*,COUNT(sv.ma_sinh_vien) AS so_thanh_vien from do_an_tot_nghiep.nhom_datn nda
            JOIN sinh_vien sv ON sv.nhom_id = nda.id) n
            ON n.id= s.nhom_id
            LEFT JOIN do_an_tot_nghiep.chuyen_nganh c ON c.id= s.chuyen_nganh_id
            LEFT JOIN do_an_tot_nghiep.mon_datn m ON m.id = s.mon_datn_id
            LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan g ON g.id = n.giang_vien_id
            LEFT JOIN do_an_tot_nghiep.dot_dang_ky d ON d.id =s.dot_dang_ky_id
            LEFT JOIN do_an_tot_nghiep.mon_datn mct ON mct.id = s.mon_chuong_trinh_id
            WHERE (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL) AND 
            (s.chuyen_nganh_id = :#{#req.idChuyenNganh} OR  :#{#req.idChuyenNganh} IS NULL OR :#{#req.idChuyenNganh} LIKE '')
            AND (if('b'= :#{#req.idTrangThaiNhom},nhom_id IS NULL,if('a'= :#{#req.idTrangThaiNhom},nhom_id IS NOT NULL, (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL))))
            AND d.id =:#{#req.idDotDangKy} AND s.co_so_id = :#{#req.coSoId}
            GROUP BY s.id 
            ORDER BY s.last_modified_date DESC """,
            countQuery = """
                     SELECT count(s.id) 
                    FROM do_an_tot_nghiep.sinh_vien s 
                    LEFT JOIN do_an_tot_nghiep.nhom_datn n ON n.id= s.nhom_id 
                    LEFT JOIN do_an_tot_nghiep.chuyen_nganh c ON c.id= s.chuyen_nganh_id 
                    LEFT JOIN do_an_tot_nghiep.mon_datn m ON m.id = s.mon_datn_id 
                    LEFT JOIN do_an_tot_nghiep.giang_vien_huong_dan g ON g.id = n.giang_vien_id 
                    LEFT JOIN do_an_tot_nghiep.dot_dang_ky d ON d.id =s.dot_dang_ky_id
                    LEFT JOIN do_an_tot_nghiep.mon_datn mct ON mct.id = s.mon_chuong_trinh_id
                    WHERE (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL) AND 
                    (s.chuyen_nganh_id = :#{#req.idChuyenNganh} OR  :#{#req.idChuyenNganh} IS NULL OR :#{#req.idChuyenNganh} LIKE '')
                    AND (if('b'= :#{#req.idTrangThaiNhom},nhom_id IS NULL,if('a'= :#{#req.idTrangThaiNhom},nhom_id IS NOT NULL, (s.ma_sinh_vien LIKE %:#{#req.tenSinhVien}% OR s.ten_sinh_vien LIKE %:#{#req.tenSinhVien}% OR :#{#req.tenSinhVien} IS NULL))))
                    AND  d.id =:#{#req.idDotDangKy} AND s.co_so_id = :#{#req.coSoId}
                    GROUP BY s.id  
                    """, nativeQuery = true)
    List<DtSinhVienTheoDotResponse> exportExcel(@Param("req") FindDtSinhVienTheoDotRequest req);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE do_an_tot_nghiep.sinh_vien s
            SET s.trang_thai ='1'
            WHERE s.id= :idSV
            """, nativeQuery = true)
    int loaiSinhVien(@Param("idSV") String id);
}
