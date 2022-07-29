package com.fpolydatn.service.impl;

import com.fpolydatn.model.ChuyenNganh;
import com.fpolydatn.model.CoSo;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Vinhnv
 */

@Service
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    @Override
    public Object getList(UUID id, int offset, int size) {
        Optional<CoSo>  optionalCoSo = coSoRepository.findById(id);
        if(optionalCoSo.isPresent()){
           return chuyenNganhRepository.getListChuyenNganh(id,offset, size) ;
        }
        return null ;
    }
}
