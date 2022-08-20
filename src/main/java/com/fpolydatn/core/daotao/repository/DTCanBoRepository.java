package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.FindCanBoRequest;
import com.fpolydatn.core.daotao.model.response.CanBoResponse;
import com.fpolydatn.repository.CanBoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author caodinh
 */
public interface DTCanBoRepository extends CanBoRepository {


    @Query(value = """
            SELECT  cb.id,cb.email_fe,cb.email_fpt,cb.so_dien_thoai,cb.last_modified_date,
                       cb.ten_can_bo,cb.ten_tai_khoan ,cs.ten_co_so
                       FROM do_an_tot_nghiep.can_bo cb 
                       JOIN do_an_tot_nghiep.co_so cs
                       ON cb.co_so_id= cs.id  WHERE 
                           (:#{#req.tenCanBo} IS NULL
                           OR :#{#req.tenCanBo} LIKE ''
                           OR ten_can_bo LIKE %:#{#req.tenCanBo}% )
                       ORDER BY last_modified_date DESC 
            """, nativeQuery = true)
    Page<CanBoResponse> findAllByName(@Param("req") FindCanBoRequest canBoRequest, Pageable page);

}
