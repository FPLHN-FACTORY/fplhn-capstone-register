package com.fpolydatn.repository;

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
            " ten_tai_khoan ," +
            " vai_tro_id  " +
            " FROM can_bo cb limit :size offset :offset", nativeQuery = true)
    List<CanBo> getListCanBo(@Param("offset") int offset, @Param("size") int size);
}
