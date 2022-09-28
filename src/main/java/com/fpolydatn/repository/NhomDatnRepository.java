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
                SELECT IF(COUNT(id) = 0, 0, MAX(SUBSTRING(ma_nhom, 4))) FROM nhom_datn 
                WHERE dot_dang_ky_id = :dotDangKyId
            """, nativeQuery = true)
    String genMaNhomTuDong(@Param("dotDangKyId") String dotDangKyId);
}
