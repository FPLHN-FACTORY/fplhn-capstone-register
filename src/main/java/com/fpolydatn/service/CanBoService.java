package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.model.CanBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author nguyenvv4
 */
public interface CanBoService {

    List<CanBo> getList(int offset, int size);

    CanBo addNew(CreateCanBoRequest canBo);
}
