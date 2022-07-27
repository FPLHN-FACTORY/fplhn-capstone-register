package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.model.VaiTro;
import com.fpolydatn.repository.VaiTroRepository;
import com.fpolydatn.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VaiTroServiceImpl implements VaiTroService {

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Override
    public List<VaiTro> getList(int offset, int size) {
        return vaiTroRepository.getListVaiTro(offset, size);
    }
}
