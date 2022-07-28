package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateCoSoRequest;
import com.fpolydatn.infrastructure.request.UpdateCoSoRequest;
import com.fpolydatn.model.CoSo;

import java.util.UUID;

/**
 * @author caodinh
 */
public interface CoSoService {

    CoSo addNew(CreateCoSoRequest canBo);

    CoSo update(UUID uuid, UpdateCoSoRequest coSo);
}
