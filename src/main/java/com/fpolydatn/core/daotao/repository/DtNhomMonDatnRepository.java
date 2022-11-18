package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtDetailNhomMonResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.repository.MonDatnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DtNhomMonDatnRepository extends MonDatnRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) AS
                    stt, m.id, m.ma_mon, m.ten_mon_datn,cn.ten_chuyen_nganh,
                    (SELECT  COUNT(n.id) FROM mon_datn n where n.nhom_mon_datn_id = m.id) AS so_mon_datn
                    FROM mon_datn m JOIN chuyen_nganh cn ON m.chuyen_nganh_id = cn.id  
                    WHERE m.co_so_id = :#{#req.coSoId}
                    AND m.nhom_mon_datn_id IS NULL
                    ORDER BY m.last_modified_date DESC                                            
             """, countQuery = """
                        SELECT COUNT(m.id)
                        FROM mon_datn m
                        WHERE m.co_so_id = :#{#req.coSoId}
                        AND m.nhom_mon_datn_id IS NULL 
                        ORDER BY m.last_modified_date DESC 
            """, nativeQuery = true)
    Page<DtNhomMonDatnResponse> findAllByCoSo(@Param("req") DtFindNhomMonDatnRequest req, Pageable page);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) AS stt, m.ma_mon, m.ten_mon_datn
            FROM mon_datn m WHERE m.co_so_id = :coSoId
            AND m.nhom_mon_datn_id =:nhomMonID
            ORDER BY m.last_modified_date DESC 
             """, countQuery = """
            SELECT COUNT(m.id)
            FROM mon_datn m
            WHERE m.co_so_id = :coSoId 
            AND AND m.nhom_mon_datn_id =:nhomMonID                       
            ORDER BY m.last_modified_date DESC 
            """, nativeQuery = true)
    List<DtDetailNhomMonResponse> findMonById(@Param("nhomMonID") String id, @Param("coSoId") String coSoId);

    @Query(value = """
                SELECT * 
                FROM mon_datn
                WHERE ma_mon = :maMon 
                AND co_so_id = :coSoId
            """, nativeQuery = true)
    MonDatn findByMaNhomMon(@Param("maMon") String maMon, @Param("coSoId") String coSoId);

}
