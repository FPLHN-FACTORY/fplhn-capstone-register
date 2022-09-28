package com.fpolydatn.infrastructure.session;

import com.fpolydatn.infrastructure.constant.SessionConstant;
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
        return String.valueOf(session.getAttribute(SessionConstant.CO_SO_ID));
    }

    @Override
    public String getUserId() {
        return String.valueOf(session.getAttribute(SessionConstant.USER_ID));
    }
}
