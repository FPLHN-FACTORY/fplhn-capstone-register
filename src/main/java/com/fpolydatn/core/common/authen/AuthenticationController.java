package com.fpolydatn.core.common.authen;

import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.infrastructure.constant.ActorConstant;
import com.fpolydatn.infrastructure.constant.SessionConstant;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.CoSoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier(CoSoRepository.NAME)
    private CoSoRepository coSoRepository;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/giang-vien")
    public String gvChonCoSo(Model model, HttpSession session) {
        return getCoSo(model, session, ActorConstant.GIANG_VIEN);
    }

    @GetMapping("/sinh-vien")
    public String svChonCoSo(Model model, HttpSession session) {
        return getCoSo(model, session, ActorConstant.SINH_VIEN);
    }

    @GetMapping("/dao-tao")
    public String dtChonCoSo(Model model, HttpSession session) {
        return getCoSo(model, session, ActorConstant.DAO_TAO);
    }

    @GetMapping("/chu-nhiem")
    public String cnGetCoSo(Model model, HttpSession session) {
        return getCoSo(model, session, ActorConstant.CHU_NHIEM);
    }

    private String getCoSo(Model model, HttpSession session, String role) {
        List<SimpleEntityProj> listCoSo = coSoRepository.findAllSimpleEntity();
        model.addAttribute("listCoSo", listCoSo);
        session.setAttribute("role", role);
        return "authen/chon-co-so";
    }

    @GetMapping("/{coSoId}")
    @ResponseBody
    public ResponseObject getData(@PathVariable("coSoId") String coSoId, HttpSession session) {
        session.setAttribute(SessionConstant.CO_SO_ID, coSoId);
        return new ResponseObject(true);
    }

    @GetMapping("/403")
    public String prohibitedAccess() {
        return "403";
    }

}
