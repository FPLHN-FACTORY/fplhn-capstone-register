package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.model.CanBo;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.service.CanBoService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nguyenvv4
 */
@Service
public class CanBoServiceImpl implements CanBoService {

    @Autowired
    private CanBoRepository canBoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public List<CanBo> getList(int offset, int size) {
        return canBoRepository.getListCanBo(offset, size);
    }

    @Override
    public CanBo addNew(CreateCanBoRequest canBoRequest) {
        CanBo canBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        return canBoRepository.save(canBo);
    }
}
