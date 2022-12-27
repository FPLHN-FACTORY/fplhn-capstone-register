package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.request.DtFindPhanMonGiangVienHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanMonDatnChoGiangVienHuongDanResponse;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.repository.PhanCongHuongDanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author huynq
 */
public interface DtPhanCongHuongDanRepository extends PhanCongHuongDanRepository {
    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY gv.last_modified_date DESC) AS stt
            , gv.id, gv.ten_gvhd, ddk.ten_dot_dang_ky FROM do_an_tot_nghiep.phan_cong_huong_dan pc
            JOIN do_an_tot_nghiep.giang_vien_huong_dan gv ON pc.giang_vien_id = gv.id
                JOIN dot_dang_ky ddk ON gv.dot_dang_ky_id = ddk.id 
            WHERE pc.mon_datn_id = :monID 
             AND
                ( :#{#req.tenGV} IS NULL 
                    OR :#{#req.tenGV} LIKE '' 
                    OR gv.ten_gvhd LIKE %:#{#req.tenGV}% ) 
                    AND gv.co_so_id = :#{#req.coSoId}
                    ORDER BY gv.last_modified_date DESC 
            """, countQuery = """
            SELECT  COUNT(pc.giang_vien_id) FROM do_an_tot_nghiep.phan_cong_huong_dan pc
            JOIN do_an_tot_nghiep.giang_vien_huong_dan gv ON pc.giang_vien_id = gv.id
                JOIN dot_dang_ky ddk ON gv.dot_dang_ky_id = ddk.id 
            WHERE pc.mon_datn_id = :monID AND
                ( :#{#req.tenGV} IS NULL 
                    OR :#{#req.tenGV} LIKE '' 
                    OR gv.ten_gvhd LIKE %:#{#req.tenGV}% ) 
                    AND gv.co_so_id = :#{#req.coSoId}
                    ORDER BY gv.last_modified_date DESC 
            """
            , nativeQuery = true)
    Page<DtPhanCongHuongDanResponse> getGiangVienHuongDan(@Param("monID") String id, @Param("req") DtFindPhanCongHuongDanRequest req, Pageable page);

    @Query(value = """
            SELECT a.id, a.created_date, a.last_modified_date, c.ten_co_so,
            a.ma_mon, a.ten_mon_datn, b.ten_chuyen_nganh, a.co_so_id, a.chuyen_nganh_id
                        FROM do_an_tot_nghiep.mon_datn a
                        JOIN do_an_tot_nghiep.chuyen_nganh b ON a.chuyen_nganh_id = b.id
                        JOIN do_an_tot_nghiep.co_so c ON a.co_so_id = c.id
                        WHERE a.id = :maMonId 
                        """, nativeQuery = true)
    DtPhanCongHuongDanMonDatnResponse getMon(@Param("maMonId") String maMonId);

    @Query(value = """
            SELECT ROW_NUMBER() OVER() AS stt, gv.id, gv.ten_gvhd, ddk.ten_dot_dang_ky
             FROM do_an_tot_nghiep.giang_vien_huong_dan gv
                JOIN dot_dang_ky ddk ON gv.dot_dang_ky_id = ddk.id 
            WHERE gv.co_so_id = :coSoId
            AND gv.id NOT IN (SELECT giang_vien_id FROM do_an_tot_nghiep.phan_cong_huong_dan WHERE mon_datn_id = :maMonId)
            """
            , nativeQuery = true)
    List<DtPhanCongHuongDanResponse> getPhanCongGiaoVien(@Param("coSoId") String coSoId, @Param("maMonId") String maMonId);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) AS stt, m.id, m.ten_mon_datn, m.ma_mon FROM do_an_tot_nghiep.mon_datn m
            JOIN do_an_tot_nghiep.phan_cong_huong_dan pc on m.id = pc.mon_datn_id
            WHERE pc.giang_vien_id = :giangVienId
              AND m.nhom_mon_datn_id IS NOT NULL
             AND
                ( :#{#req.tenMonDatn} IS NULL 
                    OR :#{#req.tenMonDatn} LIKE '' 
                    OR m.ten_mon_datn LIKE %:#{#req.tenMonDatn}% ) 
                    ORDER BY last_modified_date DESC 
            """, countQuery = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) AS stt, m.id, m.ten_mon_datn FROM do_an_tot_nghiep.mon_datn m
            JOIN do_an_tot_nghiep.phan_cong_huong_dan pc on m.id = pc.mon_datn_id
            WHERE pc.giang_vien_id = :giangVienId
              AND m.nhom_mon_datn_id IS NOT NULL 
             AND
                ( :#{#req.tenMonDatn} IS NULL 
                    OR :#{#req.tenMonDatn} LIKE '' 
                    OR m.ten_mon_datn LIKE %:#{#req.tenMonDatn}% ) 
                    ORDER BY last_modified_date DESC 
            """
            , nativeQuery = true)
    Page<DtPhanMonDatnChoGiangVienHuongDanResponse> getMonDatn(@Param("giangVienId") String id, @Param("req") DtFindPhanMonGiangVienHuongDanRequest req, Pageable page);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY gvhd.last_modified_date DESC ) AS stt,
                gvhd.id,
                cs.ten_co_so,
                ddk.ten_dot_dang_ky,
                gvhd.ten_gvhd ,
                gvhd.so_nhom_huong_dan_toi_da,
                gvhd.ten_tai_khoan,
                gvhd.so_dien_thoai,
                gvhd.email_fpt, 
                gvhd.email_fe 
                FROM giang_vien_huong_dan gvhd 
                JOIN dot_dang_ky ddk ON gvhd.dot_dang_ky_id = ddk.id 
                JOIN co_so cs ON gvhd.co_so_id = cs.id 
                WHERE gvhd.id = :maGiangVienHuongDan 
                AND gvhd.co_so_id = :coSoId""", nativeQuery = true)
    DtGiangVienHDResponse getGiangVienHD(@Param("maGiangVienHuongDan") String maGiangVienHuongDan, @Param("coSoId") String coSoId);

    @Query(value = """
            SELECT ROW_NUMBER() OVER() AS stt, m.id, m.ten_mon_datn, m.ma_mon FROM do_an_tot_nghiep.mon_datn m
            WHERE m.co_so_id = :coSoId AND nhom_mon_datn_id IS NOT NULL
            AND m.id NOT IN (SELECT mon_datn_id FROM do_an_tot_nghiep.phan_cong_huong_dan WHERE giang_vien_id = :maGiangVienHuongDan)
            """
            , nativeQuery = true)
    List<DtPhanMonDatnChoGiangVienHuongDanResponse> getPhanCongMonDatn(@Param("coSoId") String coSoId, @Param("maGiangVienHuongDan") String maMonId);

}