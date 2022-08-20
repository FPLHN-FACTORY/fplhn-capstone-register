package com.fpolydatn.core.chunhiem.controller.infrastructure.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author phongtt35
 */

@Component
class FpolyDatnSessionImpl implements FpolyDatnSession {

    @Autowired
    private HttpSession session;

    @Override
    public String getCoSoId() {
        return String.valueOf(session.getAttribute("coSoIDSelected"));
    }
}
