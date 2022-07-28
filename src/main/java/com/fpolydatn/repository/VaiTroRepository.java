package com.fpolydatn.repository;

import com.fpolydatn.model.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VaiTroRepository extends JpaRepository<VaiTro, UUID> {
    @Query(value = "SELECT vai_tro_id, ten_vai_tro FROM vai_tro LIMIT :size offset :offset", nativeQuery = true)
    List<VaiTro> getList(@Param("offset") int offset, @Param("size") int size);
}
