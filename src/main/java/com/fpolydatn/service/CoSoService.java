package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.coso.CreateCoSoRequest;
import com.fpolydatn.infrastructure.request.coso.UpdateCoSoRequest;
import com.fpolydatn.model.domain.CoSo;

import java.util.List;
import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface CoSoService {

    List<CoSo> getListCoSo();

    CoSo deleteCoSo(UUID id);

    CoSo addNew(CreateCoSoRequest canBo);

    CoSo update(UUID id, UpdateCoSoRequest coSo);

    CoSo findById(UUID id);

}
