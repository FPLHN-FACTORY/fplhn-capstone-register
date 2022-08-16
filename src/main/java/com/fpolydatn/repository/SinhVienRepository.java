package com.fpolydatn.repository;

import com.fpolydatn.infrastructure.response.SinhVienResponse;
import com.fpolydatn.model.domain.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author phongtt35
 */
@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, UUID> {

    @Query(value = "SELECT sv.id,sv.email,sv.ten_sinh_vien,cs.ten_co_so FROM sinh_vien sv INNER JOIN co_so cs " +
            "ON sv.co_so_id = cs.id " +
            "WHERE sv.email =:emailFPT AND cs.id = :coSoId", nativeQuery = true)
    SinhVienResponse findSinhVienByEmailAndCoSo(@Param("coSoId") String id, @Param("emailFPT") String emailFPT);

}
