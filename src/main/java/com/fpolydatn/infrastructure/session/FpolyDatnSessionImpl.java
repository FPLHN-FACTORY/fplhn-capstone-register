package com.fpolydatn.infrastructure.session;

import com.fpolydatn.repository.CoSoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author phongtt35
 */

@Component
class FpolyDatnSessionImpl implements FpolyDatnSession {

    @Autowired
    private HttpSession session;

    @Autowired
    @Qualifier(CoSoRepository.NAME)
    private CoSoRepository repository;

    @Override
    public String getCoSoId() {
        return repository.findAll().get(0).getId();
        //return String.valueOf(session.getAttribute("coSoIDSelected"));
    }
}
