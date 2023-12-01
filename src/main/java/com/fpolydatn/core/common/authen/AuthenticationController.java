package com.fpolydatn.core.common.authen;

import com.fpolydatn.infrastructure.constant.SessionConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author phongtt35
 */

@Controller
@RequestMapping("/authentication")
public final class AuthenticationController {

    @PostMapping
    public String login(@RequestParam String campus, @RequestParam String role, HttpSession session) {
        session.setAttribute("role", role);
        session.setAttribute(SessionConstant.CO_SO_ID, campus);
        return "redirect:/oauth2/authorization/google";
    }
}
