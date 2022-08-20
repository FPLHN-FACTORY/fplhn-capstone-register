package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.model.response.TenCanBoResponse;
import com.fpolydatn.core.daotao.repository.DtCanBoRepository;
import com.fpolydatn.core.daotao.service.CanBoService;
import com.fpolydatn.repository.CanBoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanBoServiceImpl implements CanBoService {

    @Autowired
    private DtCanBoRepository canBoRepository;

    @Override
    public List<TenCanBoResponse> getTenCanBo() {
        return canBoRepository.findCanBoByVaiTro();
    }
}
