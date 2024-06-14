package com.fpolydatn.core.common.authen;

import com.fpolydatn.infrastructure.constant.SessionConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author phongtt35
 */

@Controller
@RequestMapping
public final class AuthenticationController {

    @PostMapping("/authentication")
    public String login(@RequestParam String campus, @RequestParam String role, HttpSession session) {
        session.setAttribute("role", role);
        session.setAttribute(SessionConstant.CO_SO_ID, campus);
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/authentication-failure")
    public String loginFailure(HttpSession session, @RequestParam String error) {
        session.setAttribute("errorMess", error);
        return "redirect:/";
    }

}
