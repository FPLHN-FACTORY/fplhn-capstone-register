package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.daotao.model.request.FindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanGiaoVienResponse;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanResponse;
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
            JOIN do_an_tot_nghiep.giang_vien_huong_dan gv on pc.giang_vien_id = gv.id
            WHERE pc.mon_datn_id = :monID AND
                ( :#{#req.tenGV} IS NULL 
                    OR :#{#req.tenGV} LIKE '' 
                    OR gv.ten_gvhd LIKE %:#{#req.tenGV}% ) 
                    ORDER BY last_modified_date DESC 
            """,countQuery = """
            SELECT  COUNT(pc.giang_vien_id) FROM do_an_tot_nghiep.phan_cong_huong_dan pc
            JOIN do_an_tot_nghiep.giang_vien_huong_dan gv on pc.giang_vien_id = gv.id
            WHERE pc.mon_datn_id = :monID AND
                ( :#{#req.tenGV} IS NULL 
                    OR :#{#req.tenGV} LIKE '' 
                    OR gv.ten_gvhd LIKE %:#{#req.tenGV}% ) 
                    ORDER BY last_modified_date DESC 
            """
            , nativeQuery = true)
    Page<PhanCongHuongDanResponse> findByName(@Param("monID") String id , @Param("req") FindPhanCongHuongDanRequest req, Pageable page);

    @Query(value = "SELECT a.id, a.created_date, a.last_modified_date, c.ten_co_so,\n" +
            "a.ma_mon, a.ten_mon_datn, b.ten_chuyen_nganh, a.co_so_id, a.chuyen_nganh_id \n" +
            "            FROM do_an_tot_nghiep.mon_datn a\n" +
            "            join do_an_tot_nghiep.chuyen_nganh b on a.chuyen_nganh_id = b.id\n" +
            "            join do_an_tot_nghiep.co_so c on a.co_so_id = c.id\n" +
            "            WHERE a.id = :maMonId", nativeQuery = true)
    PhanCongHuongDanMonDatnResponse getMon(@Param("maMonId") String maMonId);

    @Query(value = "SELECT a.giang_vien_id, a.mon_datn_id\n" +
            " FROM do_an_tot_nghiep.phan_cong_huong_dan a\n" +
            " WHERE a.giang_vien_id = :maGV", nativeQuery = true)
    PhanCongHuongDan finId(@Param("maGV") String id);

    @Query(value = "SELECT ROW_NUMBER() OVER() AS stt, gv.id, gv.ten_gvhd FROM do_an_tot_nghiep.giang_vien_huong_dan gv\n" +
            "where gv.co_so_id = :coSoId \n" +
            "and gv.id not in (select giang_vien_id from do_an_tot_nghiep.phan_cong_huong_dan where mon_datn_id = :maMonId)\n" +
            "and gv.id not in (SELECT c.giang_vien_id FROM do_an_tot_nghiep.phan_cong_huong_dan c WHERE c.giang_vien_id = gv.id\n" +
            "\t group by c.giang_vien_id\n" +
            "\t having count(c.giang_vien_id) <= gv.so_nhom_huong_dan_toi_da)\n" +
            ";"
            , nativeQuery = true)
    List<PhanCongHuongDanGiaoVienResponse> getPhanCongGiaoVien(@Param("coSoId") String coSoId, @Param("maMonId") String maMonId);
}