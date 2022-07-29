package com.fpolydatn.service.impl;

import com.fpolydatn.model.ChuyenNganh;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vinhnv
 */

@Service
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;

    @Override
    public List<ChuyenNganh> getList(int offset, int size) {
        return chuyenNganhRepository.getListChuyenNganh(offset, size);
    }
}
