package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.request.SvAddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienTheoNhomResponse;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author thangdt
 */
public interface SvSinhVienRepository extends SinhVienRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER() AS stt,  
                    ma_sinh_vien, ten_sinh_vien, 
                    so_dien_thoai, email, khoa 
            FROM sinh_vien
            WHERE  nhom_id LIKE :#{#request.maNhom}
            """, nativeQuery = true)
    List<SvSinhVienTheoNhomResponse> findByNhomId(@Param("request") SvFindSinhVienRequest request);

    @Query(value = "SELECT nhom_id FROM sinh_vien " +
            "WHERE co_so_id = :coSoId " +
            "AND dot_dang_ky_id = :dotDangKyId " +
            "AND id = :id", nativeQuery = true)
    String getNhomIdById(@Param("coSoId") String coSoId, @Param("dotDangKyId") String dotDangKyId, @Param("id") String id);

    @Query(value = "SELECT COUNT(id) FROM sinh_vien WHERE nhom_id = :nhomId GROUP BY nhom_id", nativeQuery = true)
    int getSoLuongThanhVienById(@Param("nhomId") String nhomId);

}
