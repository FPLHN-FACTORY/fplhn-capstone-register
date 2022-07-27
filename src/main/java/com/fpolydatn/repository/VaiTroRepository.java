package com.fpolydatn.repository;

import com.fpolydatn.model.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface VaiTroRepository extends JpaRepository<VaiTro, UUID> {

    @Query (value = "select vai_tro_id, ten_vai_tro from vai_tro limit :size offset :offset", nativeQuery = true)
    List<VaiTro> getListVaiTro(@Param("offset") int offset, @Param("size") int size);

}
