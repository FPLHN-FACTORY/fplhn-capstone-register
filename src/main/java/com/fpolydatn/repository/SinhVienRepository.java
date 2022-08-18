package com.fpolydatn.repository;

import com.fpolydatn.model.entity.SinhVienEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


/**
 * @author phongtt35
 */
public interface SinhVienRepository extends JpaRepository<SinhVienEntity, String> {

//    @Query(value = """
//            SELECT COUNT(1) > 0 FROM sinh_vien sv
//            WHERE sv.co_so_id=:coSoId AND sv.email=:email
//           """, nativeQuery = true)
    boolean existsByEmailAndCoSoId(@Param("coSoId") String id, @Param("email") String email);
}
