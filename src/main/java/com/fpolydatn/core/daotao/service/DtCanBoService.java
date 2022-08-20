package com.fpolydatn.core.daotao.service;



import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.CreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.FindCanBoRequest;
import com.fpolydatn.core.daotao.model.request.UpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.response.CanBoResponse;
import com.fpolydatn.core.daotao.model.response.TenCanBoResponse;
import com.fpolydatn.entity.CanBo;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author nguyenvv4
 */
public interface DtCanBoService {

    PageableObject<CanBoResponse> search(final FindCanBoRequest canBoRequest);

    CanBo create(@Valid CreateCanBoRequest canBo);

    Boolean delete(UUID id);

    CanBo findById(UUID id);

    CanBo update(UUID id, UpdateCanBoRequest canBoRequest);

//    Long countCanBoByCoSoId(UUID coSoId);

    List<TenCanBoResponse> findCanBoByVaiTro();

}