package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.model.response.DtTenCanBoResponse;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.repository.CanBoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author caodinh
 */
public interface DtCanBoRepository extends CanBoRepository {

    @Query(value = """
            SELECT  ROW_NUMBER() OVER(ORDER BY cb.last_modified_date DESC) AS STT, cb.id,cb.email_fe,
                    cb.email_fpt,cb.so_dien_thoai,cb.last_modified_date,
                    cb.ten_can_bo,cb.ten_tai_khoan ,cb.vai_tro
                    FROM do_an_tot_nghiep.can_bo cb 
                    JOIN do_an_tot_nghiep.co_so cs
                    ON cb.co_so_id= cs.id  WHERE (:#{#req.tenCanBo} IS NULL
                    OR :#{#req.tenCanBo} LIKE ''
                    OR ten_can_bo LIKE %:#{#req.tenCanBo}% )
                    AND cb.co_so_id = :#{#req.coSoID}
                    ORDER BY cb.last_modified_date DESC 
            """,
            countQuery = """
                    SELECT  COUNT(cb.id)
                            FROM do_an_tot_nghiep.can_bo cb 
                            JOIN do_an_tot_nghiep.co_so cs
                            ON cb.co_so_id= cs.id  WHERE (:#{#req.tenCanBo} IS NULL
                            OR :#{#req.tenCanBo} LIKE ''
                            OR ten_can_bo LIKE %:#{#req.tenCanBo}% )
                            ORDER BY cb.last_modified_date DESC 
                    """,
            nativeQuery = true)
    Page<DtCanBoResponse> findAllByTenCanBo(@Param("req") DtFindCanBoRequest canBoRequest, Pageable page);

    @Query(value = """
            SELECT id,ten_can_bo FROM can_bo WHERE vai_tro = 2""", nativeQuery = true)
    List<DtTenCanBoResponse> findCanBoByVaiTro();

    Optional<CanBo> findByEmailFe(String emailfe);

}
