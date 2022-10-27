package com.fpolydatn.core.chunhiem.repository;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnGiangVienHuongDanReponsitory extends GiangVienHuongDanRepository {
    @Query(value = """
            SELECT id FROM giang_vien_huong_dan WHERE ten_tai_khoan = :tenTaiKhoan
            """, nativeQuery = true)
    String getIdFromGiangVienId(@Param("tenTaiKhoan") String tenTaiKhoan);

    @Query(value = """
            SELECT CONCAT(ten_tai_khoan, '_' , ten_gvhd) AS thong_tin FROM giang_vien_huong_dan
            WHERE ((:#{#req.tenTaiKhoan} IS NULL OR :#{#req.tenTaiKhoan} LIKE ''
            OR ten_tai_khoan LIKE %:#{#req.tenTaiKhoan}%)
            OR (:#{#req.tenGvhd} IS NULL OR :#{#req.tenGvhd} LIKE ''
            OR ten_gvhd LIKE %:#{#req.tenGvhd}%))
            AND co_so_id = :#{#req.coSoId} AND dot_dang_ky_id = :#{#req.dotDangKyId}
            """, nativeQuery = true)
    List<String> getListGvhd(@Param("req") CnFindGvhdRequest req);


    @Query(value = "select giang_vien_id from phan_cong_huong_dan where giang_vien_id = :idGiangVien" +
            " and mon_datn_id = :idMonDatn",nativeQuery = true)
    String checkIdGiangVienAndMonDatn(@Param("idGiangVien")String idGiangVien,@Param("idMonDatn") String idMonDatn);

}
