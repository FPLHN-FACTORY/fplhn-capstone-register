package com.fpolydatn.core.daotao.repository;

import com.fpolydatn.core.daotao.model.response.TenCanBoResponse;
import com.fpolydatn.repository.CanBoRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author hoangnt
 */
public interface DtCanBoRepository extends CanBoRepository {

    @Query(value = """
            SELECT id,ten_can_bo FROM can_bo WHERE vai_tro = 2""", nativeQuery = true)
    List<TenCanBoResponse> findCanBoByVaiTro();
}
