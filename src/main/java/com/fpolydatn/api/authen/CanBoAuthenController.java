package com.fpolydatn.api.authen;

import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.service.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author HangNT
 */
@Controller
public class CanBoAuthenController {

    @Autowired
    private CoSoService coSoService;

    @GetMapping("/cb-chon-co-so")
    public String getCoSo(Model model, HttpSession session) {
        List<CoSo> listCoSo = coSoService.getListCoSo();
        model.addAttribute("listCoSo", listCoSo);
        session.setAttribute("role", "cb");
        return "authen/chon-co-so";
    }

}
