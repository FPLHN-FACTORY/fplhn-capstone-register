package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanGiaoVienResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
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
            SELECT ROW_NUMBER() OVER(ORDER BY last_modified_date DESC) AS stt, gv.id, gv.ten_gvhd FROM do_an_tot_nghiep.phan_cong_huong_dan pc
            JOIN do_an_tot_nghiep.giang_vien_huong_dan gv ON pc.giang_vien_id = gv.id
            WHERE pc.mon_datn_id = :monID AND
                ( :#{#req.tenGV} IS NULL 
                    OR :#{#req.tenGV} LIKE '' 
                    OR gv.ten_gvhd LIKE %:#{#req.tenGV}% ) 
                    ORDER BY last_modified_date DESC 
            """, countQuery = """
            SELECT  COUNT(pc.giang_vien_id) FROM do_an_tot_nghiep.phan_cong_huong_dan pc
            JOIN do_an_tot_nghiep.giang_vien_huong_dan gv ON pc.giang_vien_id = gv.id
            WHERE pc.mon_datn_id = :monID AND
                ( :#{#req.tenGV} IS NULL 
                    OR :#{#req.tenGV} LIKE '' 
                    OR gv.ten_gvhd LIKE %:#{#req.tenGV}% ) 
                    ORDER BY last_modified_date DESC 
            """
            , nativeQuery = true)
    Page<DtPhanCongHuongDanResponse> findByName(@Param("monID") String id, @Param("req") DtFindPhanCongHuongDanRequest req, Pageable page);

    @Query(value = """
            SELECT a.id, a.created_date, a.last_modified_date, c.ten_co_so,
            a.ma_mon, a.ten_mon_datn, b.ten_chuyen_nganh, a.co_so_id, a.chuyen_nganh_id
                        FROM do_an_tot_nghiep.mon_datn a
                        JOIN do_an_tot_nghiep.chuyen_nganh b ON a.chuyen_nganh_id = b.id
                        JOIN do_an_tot_nghiep.co_so c ON a.co_so_id = c.id
                        WHERE a.id = :maMonId""", nativeQuery = true)
    DtPhanCongHuongDanMonDatnResponse getMon(@Param("maMonId") String maMonId);

    @Query(value = """
            SELECT a.giang_vien_id, a.mon_datn_id
            FROM do_an_tot_nghiep.phan_cong_huong_dan a
            WHERE a.giang_vien_id = :maGV""", nativeQuery = true)
    PhanCongHuongDan finId(@Param("maGV") String id);

    @Query(value = """
            SELECT ROW_NUMBER() OVER() AS stt, gv.id, gv.ten_gvhd FROM do_an_tot_nghiep.giang_vien_huong_dan gv
            WHERE gv.co_so_id = :coSoId 
            AND gv.id NOT IN (SELECT giang_vien_id FROM do_an_tot_nghiep.phan_cong_huong_dan WHERE mon_datn_id = :maMonId)
            AND gv.id NOT IN (SELECT c.giang_vien_id FROM do_an_tot_nghiep.phan_cong_huong_dan c WHERE c.giang_vien_id = gv.id
            GROUP BY c.giang_vien_id
            HAVING COUNT(c.giang_vien_id) <= gv.so_nhom_huong_dan_toi_da)
            """
            , nativeQuery = true)
    List<DtPhanCongHuongDanGiaoVienResponse> getPhanCongGiaoVien(@Param("coSoId") String coSoId, @Param("maMonId") String maMonId);
}