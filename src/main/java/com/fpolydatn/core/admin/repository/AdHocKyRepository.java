package com.fpolydatn.core.admin.repository;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.repository.HocKyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author phongtt35
 */
public interface AdHocKyRepository extends HocKyRepository {

    @Query(value = """
        SELECT ROW_NUMBER() OVER(ORDER BY created_date DESC) AS STT,
        	id, ten_hoc_ky, ngay_bat_dau, ngay_ket_thuc
            FROM hoc_ky
            WHERE 
                ( :#{#req.tenHocKy} IS NULL 
                    OR :#{#req.tenHocKy} LIKE '' 
                    OR ten_hoc_ky LIKE %:#{#req.tenHocKy}% ) 
            ORDER BY created_date DESC 
        """, countQuery = """
            SELECT COUNT(id)
                        FROM hoc_ky
                        WHERE 
                            ( :#{#req.tenHocKy} IS NULL 
                                OR :#{#req.tenHocKy} LIKE '' 
                                OR ten_hoc_ky LIKE %:#{#req.tenHocKy}% ) 
                        ORDER BY created_date DESC 
            """,nativeQuery = true)
    Page<HocKyResponse> findByName(@Param("req") FindHocKyRequest req, Pageable page);

    @Query(value = """
        SELECT COUNT(1) FROM dot_dang_ky WHERE hoc_ky_id = :hocKyId
        """, nativeQuery = true)
    Long countDotDangKy(@Param("hocKyId") String hocKyId);

    @Query(value = """
        SELECT COUNT(1) FROM hoc_ky WHERE ngay_bat_dau < :end AND ngay_ket_thuc > :start
    """, nativeQuery = true)
    Long countHocKyInPeriod(@Param("start") Long start, @Param("end") Long end);
}
