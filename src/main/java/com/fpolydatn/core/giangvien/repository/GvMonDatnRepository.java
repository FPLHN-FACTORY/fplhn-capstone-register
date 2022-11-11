package com.fpolydatn.core.giangvien.repository;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.MonDatnRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author SonPT
 */
public interface GvMonDatnRepository extends MonDatnRepository {

    @Query(value = """
                            SELECT m.id, m.ma_mon AS 'name' FROM do_an_tot_nghiep.phan_cong_huong_dan pc
                                JOIN do_an_tot_nghiep.mon_datn m ON m.id = pc.mon_datn_id
                                JOIN do_an_tot_nghiep.giang_vien_huong_dan gv ON gv.id = pc.giang_vien_id
                            WHERE gv.id = :idGvhd
                            AND m.co_so_id = :coSoId 
                            GROUP BY m.id, m.ma_mon
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntityByCoSoAndGvhd(@Param("idGvhd") String idGvhd, @Param("coSoId") String coSoId);

}
