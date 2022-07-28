package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.request.CreateVaiTroRequest;
import com.fpolydatn.model.VaiTro;
import com.fpolydatn.repository.VaiTroRepository;
import com.fpolydatn.service.VaiTroService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaiTroServiceImpl implements VaiTroService {

    @Autowired
    private VaiTroRepository vaiTroRepository;

    private FormUtils formUtil = new FormUtils();

    @Override
    public List<VaiTro> getList(int offset, int size) {
        return vaiTroRepository.getList(offset, size);
    }

    @Override
    public VaiTro create(CreateVaiTroRequest createVaiTroRequest) {
        VaiTro vaiTro = formUtil.convertToObject(VaiTro.class, createVaiTroRequest);
        return vaiTroRepository.save(vaiTro);
    }
}
