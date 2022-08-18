package com.fpolydatn.repository;

import com.fpolydatn.model.entity.GiangVienHuongDanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author phongtt35
 */
public interface GiangVienHuongDanRepository extends JpaRepository<GiangVienHuongDanEntity, String> {

    boolean existsByEmailFptAndCoSoId(@Param("coSoId") String coSoId, @Param("emailFpt") String emailFpt);
}
