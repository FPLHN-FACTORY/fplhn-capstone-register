package com.fpolydatn.repository;

import com.fpolydatn.infrastructure.response.GiangVienHuongDanResponse;
import com.fpolydatn.model.domain.GiangVienHuongDan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author phongtt35
 */
@Repository
public interface GiangVienHuongDanRepository extends JpaRepository<GiangVienHuongDan, UUID> {

    @Query(value = "SELECT gv.id,gv.ten_gvhd,gv.ten_tai_khoan,gv.email_fpt FROM giang_vien_huong_dan gv INNER JOIN co_so cs  " +
            "ON gv.co_so_id = cs.id " +
            "WHERE gv.email_fpt = :emailFPT AND cs.id=:coSoId", nativeQuery = true)
    GiangVienHuongDanResponse findGiangVienByEmailAndCoSo(@Param("coSoId") String id, @Param("emailFPT") String emailFPT);

}
