package com.fpolydatn.core.daotao.service;



import com.fpolydatn.core.daotao.model.request.CreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.UpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.response.CanBoResponse;
import com.fpolydatn.core.daotao.model.response.TenCanBoResponse;
import com.fpolydatn.entity.CanBo;

import java.util.List;
import java.util.UUID;

/**
 * @author nguyenvv4
 */
public interface CanBoService {

    List<CanBoResponse> findAllCanBoResponse(UUID coSoId, int offset, int size);

    CanBo addNew(CreateCanBoRequest canBo);

    Boolean delete(UUID id);

    CanBo findById(UUID id);

    CanBo update(UUID id, UpdateCanBoRequest canBoRequest);

    Long countCanBoByCoSoId(UUID coSoId);

    List<TenCanBoResponse> findCanBoByVaiTro();

}