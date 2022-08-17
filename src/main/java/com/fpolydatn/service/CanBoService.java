package com.fpolydatn.service;

import com.fpolydatn.infrastructure.command.canbo.CreateCanBoCommand;
import com.fpolydatn.infrastructure.command.canbo.UpdateCanBoCommand;
import com.fpolydatn.model.entity.CanBoEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author nguyenvv4
 */
public interface CanBoService {

    List<CanBoEntity> getList(int offset, int size);

    CanBoEntity addNew(CreateCanBoCommand canBo);

    CanBoEntity findById(String id);

    CanBoEntity update(String id, UpdateCanBoCommand canBoRequest);

}
