package com.fpolydatn.core.common.authen;

import com.fpolydatn.infrastructure.constant.ActorConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author thangncph26123
 */
@Controller
@RequestMapping("/admin/bit-dev")
public class AuthenticationAdminController {

    @GetMapping
    public String homeAdmin(HttpSession session) {
        session.setAttribute("role", ActorConstant.ADMIN);
        return "authen/authen-admin";
    }
}
