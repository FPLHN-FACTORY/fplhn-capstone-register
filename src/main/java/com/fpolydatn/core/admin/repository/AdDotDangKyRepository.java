package com.fpolydatn.core.admin.repository;

import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */
@Repository
public interface AdDotDangKyRepository extends DotDangKyRepository {

    @Query(value = """
			SELECT * FROM dot_dang_ky WHERE hoc_ky_id = :hocKyId LIMIT 1
            """, nativeQuery = true)
    DotDangKy findByHocKyId(@Param("hocKyId") String hocKyId);

}
