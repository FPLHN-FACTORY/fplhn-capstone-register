package com.fpolydatn.api.authen;

import com.fpolydatn.api.base.ResponseObject;
import com.fpolydatn.model.entity.CoSoEntity;
import com.fpolydatn.service.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author phongtt35
 */

@Controller
public class AuthenticationController {
    @Autowired
    private CoSoService coSoService;

    @GetMapping("/")
    public String index() {
        return "authen/index";
    }

    @GetMapping("/gv-chon-co-so")
    public String gvChonCoSo(Model model, HttpSession session) {
        List<CoSoEntity> listCoSo = coSoService.getListCoSo();
        model.addAttribute("listCoSo", listCoSo);
        session.setAttribute("role", "gv");
        return "authen/chon-co-so";
    }

    @GetMapping("/sv-chon-co-so")
    public String svChonCoSo(Model model, HttpSession session) {
        List<CoSoEntity> listCoSo = coSoService.getListCoSo();
        model.addAttribute("listCoSo", listCoSo);
        session.setAttribute("role", "sv");
        return "authen/chon-co-so";
    }

    @GetMapping("/cb-chon-co-so")
    public String getCoSo(Model model, HttpSession session) {
        List<CoSoEntity> listCoSo = coSoService.getListCoSo();
        model.addAttribute("listCoSo", listCoSo);
        session.setAttribute("role", "cb");
        return "authen/chon-co-so";
    }

    @GetMapping("/login-co-so")
    @ResponseBody
    public ResponseObject getData(@RequestParam("coSoID") String coSoId, HttpSession session) {
        session.setAttribute("coSoIDSelected", coSoId);
        return new ResponseObject(true);
    }
}
