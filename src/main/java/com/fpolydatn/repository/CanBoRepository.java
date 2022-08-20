package com.fpolydatn.repository;

import com.fpolydatn.core.daotao.model.response.CanBoResponse;
import com.fpolydatn.core.daotao.model.response.TenCanBoResponse;
import com.fpolydatn.entity.CanBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author phongtt35
 */
@Repository(CanBoRepository.NAME)
public interface CanBoRepository extends JpaRepository<CanBo, String> {

    public static final String NAME = "BaseCanBoRepository";


//    @Query(value = "SELECT COUNT(1) FROM can_bo WHERE ten_can_bo LIKE %:coSoId% ", nativeQuery = true)
//    Long countByName(@Param("tenCanBo") String tenCanBo);

    @Query(value = "SELECT id,ten_can_bo FROM can_bo where vai_tro = 2", nativeQuery = true)
    List<TenCanBoResponse> findCanBoByVaiTro();
}
