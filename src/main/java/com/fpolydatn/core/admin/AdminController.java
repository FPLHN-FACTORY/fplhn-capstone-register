package com.fpolydatn.core.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String view() {
        return "admin/home-nav";
    }
}
