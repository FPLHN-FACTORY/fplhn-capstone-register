package com.fpolydatn.core.admin.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.infrastructure.constant.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author thangncph26123
 */
@Controller
@RequestMapping("/admin/bit-dev")
public class HomeController extends BaseController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("version", Constants.VERSION);
        return "admin/home";
    }
}
