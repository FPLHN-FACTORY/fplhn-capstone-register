package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.model.response.TenCanBoResponse;
import com.fpolydatn.core.daotao.repository.DtCanBoRepository;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hoangnt
 */
@Service
public class DtCanBoServiceImpl implements DtCanBoService {

    @Autowired
    private DtCanBoRepository canBoRepository;

    @Override
    public List<TenCanBoResponse> getTenCanBo() {
        return canBoRepository.findCanBoByVaiTro();
    }
}
