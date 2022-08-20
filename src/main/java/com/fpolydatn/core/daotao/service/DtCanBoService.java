package com.fpolydatn.core.daotao.service;



import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.model.response.DtTenCanBoResponse;
import com.fpolydatn.entity.CanBo;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author nguyenvv4
 */
public interface DtCanBoService {

    PageableObject<DtCanBoResponse> search(final DtFindCanBoRequest canBoRequest);

    CanBo create(@Valid DtCreateCanBoRequest canBo);

    Boolean delete(UUID id);

    CanBo findById(UUID id);

    CanBo update(UUID id, DtUpdateCanBoRequest canBoRequest);

//    Long countCanBoByCoSoId(UUID coSoId);

    List<DtTenCanBoResponse> findCanBoByVaiTro();

}