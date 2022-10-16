package com.fpolydatn.infrastructure.session;

import com.fpolydatn.infrastructure.constant.SessionConstant;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
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
        SimpleEntityProj account = (SimpleEntityProj) session.getAttribute(SessionConstant.USER);
        return account.getId();
    }
}
