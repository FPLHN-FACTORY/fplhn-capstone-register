package com.fpolydatn.core.sinhvien.repository;

import com.fpolydatn.core.sinhvien.model.request.FindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.NhomSinhVienResponse;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    List<NhomSinhVienResponse> findByNhomId(@Param("request") FindSinhVienRequest request);
}
