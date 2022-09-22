package com.fpolydatn.repository;

import com.fpolydatn.entity.NhomDatn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository(NhomDatnRepository.NAME)
public interface NhomDatnRepository extends JpaRepository<NhomDatn, String> {

    public static final String NAME = "BaseNhomDatnRepository";

    @Query(value = """
            select '(C)' from nhom_datn where id = :idNhom and truong_nhom_id = :idSinhVien
            """, nativeQuery = true)
    String danhDauTruongNhom(@Param("idNhom") String idNhom, @Param("idSinhVien") String idSinhVien);

}
