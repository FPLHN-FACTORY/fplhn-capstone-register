package com.fpolydatn.infrastructure.session;

import com.fpolydatn.repository.CoSoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author phongtt35
 */

@Component
class FpolyDatnSession implements IFpolyDatnSession {

    @Autowired
    private CoSoRepository coSoRepository;

    @Override
    public String getCoSoId() {
        return coSoRepository.findAll().get(0).getId();
    }
}
