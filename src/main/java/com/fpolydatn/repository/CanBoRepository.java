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

    @Query(value = "SELECT ROW_NUMBER() OVER() as stt, cb.id,cb.email_fe,cb.email_fpt,cb.so_dien_thoai,\n" +
            "            cb.ten_can_bo,cb.ten_tai_khoan ,cs.ten_co_so\n" +
            "            FROM do_an_tot_nghiep.can_bo cb \n" +
            "            JOIN do_an_tot_nghiep.co_so cs\n" +
            "             ON cb.co_so_id= cs.id WHERE co_so_id = :coSoId  limit :size offset :offset", nativeQuery = true)
    List<CanBoResponse> findAllCanBoResponse(@Param("coSoId") String coSoID, @Param("offset") int offset, @Param("size") int size);

    @Query(value = "SELECT COUNT(1) FROM can_bo WHERE co_so_id = :coSoId ", nativeQuery = true)
    Long countCanBoByCoSoID(@Param("coSoId") String coSoID);

    @Query(value = "SELECT id,ten_can_bo FROM can_bo where vai_tro = 2", nativeQuery = true)
    List<TenCanBoResponse> findCanBoByVaiTro();
}
