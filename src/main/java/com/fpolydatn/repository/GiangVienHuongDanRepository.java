package com.fpolydatn.repository;


import com.fpolydatn.model.entity.GiangVienHuongDanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author phongtt35
 */
@Repository
public interface GiangVienHuongDanRepository extends JpaRepository<GiangVienHuongDanEntity, String> {

    @Query(value = "SELECT gv.id,gv.ten_gvhd,gv.ten_tai_khoan,gv.email_fpt,gv.created_date,gv.last_modified_date," +
            "  gv.co_so_id,gv.dot_dang_ky_id,gv.email_fe,gv.so_dien_thoai,gv.so_nhom_huong_dan_toi_da " +
            "   FROM giang_vien_huong_dan gv where gv.co_so_id=:coSoId AND gv.email_fpt=:emailFPT", nativeQuery = true)
    GiangVienHuongDanEntity findGiangVienByEmailAndCoSo(@Param("coSoId") String id, @Param("emailFPT") String emailFPT);

}
