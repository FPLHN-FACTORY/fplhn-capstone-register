package com.fpolydatn.core.common.authen;

import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.service.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author phongtt35
 */

@Controller
@RequestMapping("/authentication")
public final class AuthenticationController {
    @Autowired
    private CoSoService coSoService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/giang-vien")
    public String gvChonCoSo(Model model, HttpSession session) {
        List<CoSo> listCoSo = coSoService.getListCoSo();
        model.addAttribute("listCoSo", listCoSo);
        session.setAttribute("role", "gv");
        return "authen/chon-co-so";
    }

    @GetMapping("/sinh-vien")
    public String svChonCoSo(Model model, HttpSession session) {
        List<CoSo> listCoSo = coSoService.getListCoSo();
        model.addAttribute("listCoSo", listCoSo);
        session.setAttribute("role", "sv");
        return "authen/chon-co-so";
    }

    @GetMapping("/can-bo")
    public String getCoSo(Model model, HttpSession session) {
        List<CoSo> listCoSo = coSoService.getListCoSo();
        model.addAttribute("listCoSo", listCoSo);
        session.setAttribute("role", "cb");
        return "authen/chon-co-so";
    }

    @GetMapping("/{coSoId}")
    @ResponseBody
    public ResponseObject getData(@PathVariable("coSoId") String coSoId, HttpSession session) {
        session.setAttribute("coSoIDSelected", coSoId);
        return new ResponseObject(true);
    }
}
