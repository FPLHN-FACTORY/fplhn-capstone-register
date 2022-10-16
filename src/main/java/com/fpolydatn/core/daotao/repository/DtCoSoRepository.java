package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.entity.CoSo;
import com.fpolydatn.repository.CoSoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author SonPT
 */

public interface DtCoSoRepository extends CoSoRepository {

    @Query(value = """
            SELECT * FROM do_an_tot_nghiep.co_so cs
            WHERE cs.ten_co_so = :tenCoSo    ;
            """, nativeQuery = true)
    CoSo findName(@Param("tenCoSo") String tenCoSo);

}
