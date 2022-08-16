package com.fpolydatn.repository;

import com.fpolydatn.infrastructure.response.CanBoResponse;
import com.fpolydatn.model.domain.CanBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author nguyenvv4
 */
public interface CanBoRepository extends JpaRepository<CanBo, UUID> {

    @Query(value = "SELECT id , " +
            " co_so_id , " +
            " email_fe ," +
            " email_fpt ," +
            " so_dien_thoai ," +
            " ten_can_bo ," +
            " ten_tai_khoan " +
            " FROM can_bo cb limit :size offset :offset", nativeQuery = true)
    List<CanBo> getListCanBo(@Param("offset") int offset, @Param("size") int size);
    @Query(value = "SELECT cb.id,cb.email_fpt,cb.ten_tai_khoan,cs.ten_co_so FROM can_bo cb INNER JOIN co_so cs " +
            "ON cb.co_so_id = cs.id " +
            "WHERE cb.email_fpt=:emailFPT AND cs.id =:coSoId", nativeQuery = true)
    CanBoResponse findCanBoByEmailAndCoSo(@Param("coSoId") String id, @Param("emailFPT") String emailFPT);

}
