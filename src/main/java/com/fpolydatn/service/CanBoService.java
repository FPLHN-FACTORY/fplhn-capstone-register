package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.infrastructure.request.UpdateCanBoRequest;
import com.fpolydatn.model.CanBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author nguyenvv4
 */
public interface CanBoService {

    List<CanBo> getList(int offset, int size);

    CanBo addNew(CreateCanBoRequest canBo);

    CanBo findById(UUID canBoId);

    CanBo update(UUID id, UpdateCanBoRequest canBoRequest);
}
