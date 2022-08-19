package com.fpolydatn.repository;

import com.fpolydatn.model.entity.CanBoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author nguyenvv4
 */
public interface CanBoRepository extends JpaRepository<CanBoEntity, String> {

    @Query(value = "SELECT id , " +
            " co_so_id , " +
            " email_fe ," +
            " email_fpt ," +
            " so_dien_thoai ," +
            " ten_can_bo ," +
            " ten_tai_khoan " +
            " FROM can_bo cb limit :size offset :offset", nativeQuery = true)
    List<CanBoEntity> getListCanBo(@Param("offset") int offset, @Param("size") int size);

    @Query(value = "SELECT id, created_date, last_modified_date, co_so_id, email_fe, email_fpt, so_dien_thoai, " +
            "   ten_can_bo, ten_tai_khoan, vai_tro FROM can_bo cb  " +
            "WHERE cb.email_fpt=:emailFPT AND cb.co_so_id =:coSoId", nativeQuery = true)
    CanBoEntity findCanBoByEmailAndCoSo(@Param("coSoId") String id, @Param("emailFPT") String emailFPT);

}
