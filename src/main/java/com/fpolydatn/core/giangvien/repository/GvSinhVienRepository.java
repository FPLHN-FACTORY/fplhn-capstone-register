package com.fpolydatn.core.giangvien.repository;

import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienTheoNhomResponse;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author SonPT
 */
public interface GvSinhVienRepository extends SinhVienRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER() AS stt, 
                   ma_sinh_vien, ten_sinh_vien,
                   so_dien_thoai, email, khoa,
                   IF(sinh_vien.id = n.truong_nhom_id, 1, 0) AS chuc_vu,
                    mdatn.ma_mon
            FROM sinh_vien
            JOIN nhom_datn n ON n.id = sinh_vien.nhom_id
            JOIN mon_datn mdatn ON sinh_vien.mon_chuong_trinh_id = mdatn.id
            WHERE  nhom_id LIKE :#{#request.maNhom}
            AND co_so_id = :#{#request.coSoId}
            """, nativeQuery = true)
    List<SvSinhVienTheoNhomResponse> findByNhomId(@Param("request") SvFindSinhVienRequest request);

}
