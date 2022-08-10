package com.fpolydatn.repository;

import com.fpolydatn.model.DotDangKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DotDangKyRepository extends JpaRepository<DotDangKy, UUID> {

    @Query(value = "SELECT id, co_so_id, hoc_ky_id, ngay_bat_dau," +
            " ngay_ket_thuc,ten_dot_dang_ky,co_so_id FROM dot_dang_ky " +
            " WHERE co_so_id = :coSoId LIMIT :size OFFSET :offset", nativeQuery = true)
    List<DotDangKy> getListDotDangKy(@Param("coSoId") String coSoId, @Param("offset") int offset, @Param("size") int size);

}
