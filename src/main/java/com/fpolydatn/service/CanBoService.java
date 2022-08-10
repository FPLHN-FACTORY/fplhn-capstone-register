package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.canbo.CreateCanBoRequest;
import com.fpolydatn.infrastructure.request.canbo.UpdateCanBoRequest;
import com.fpolydatn.model.domain.CanBo;

import java.util.List;
import java.util.UUID;

/**
 * @author nguyenvv4
 */
public interface CanBoService {

    List<CanBo> getList(int offset, int size);

    CanBo addNew(CreateCanBoRequest canBo);

    CanBo findById(UUID id);

    CanBo update(UUID id, UpdateCanBoRequest canBoRequest);

}
