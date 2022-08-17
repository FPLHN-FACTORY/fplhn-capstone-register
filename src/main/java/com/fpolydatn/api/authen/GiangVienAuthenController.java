package com.fpolydatn.api.authen;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.response.GiangVienHuongDanResponse;
import com.fpolydatn.infrastructure.security.MyUserDetails;
import com.fpolydatn.infrastructure.security.UserDetailsService;
import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.model.domain.GiangVienHuongDan;
import com.fpolydatn.service.CoSoService;
import com.fpolydatn.service.GiangVienHuongDanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author HangNT
 */
@Controller
@RequiredArgsConstructor
public class GiangVienAuthenController {

    @Autowired
    private CoSoService coSoService;

    @Autowired
    private GiangVienHuongDanService giangVienHuongDanService;

    private final UserDetailsService userDetailsService;

    private static String authorizationRequestBaseUri
            = "oauth2/authorization";

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/index")
    public String index() {
        return "authen/index";
    }

    @GetMapping("/gv-chon-co-so")
    public String getCoSo(Model model) {
        List<CoSo> listCoSo = coSoService.getListCoSo();
        model.addAttribute("listCoSo", listCoSo);
        return "authen/chon-co-so";
    }

    @GetMapping("/login-co-so")
    @ResponseBody
    public ResponseObject getData(@RequestParam("coSoID") String coSoId, HttpSession session) {
        session.setAttribute("coSoIDSelected", coSoId);
        return new ResponseObject(true);
    }

    @GetMapping("/login-co-so-check")
    public String chuyenTrang() {
        return "authen/chuyen_trang";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/check-authen")
    public String checkAuthen(Model model, OAuth2AuthenticationToken token, HttpSession session) {
        MyUserDetails userDetails = userDetailsService.getUserDetails(token);
        String coSoIDSelected = (String) session.getAttribute("coSoIDSelected");
        try {
            GiangVienHuongDan giangVienHuongDan = giangVienHuongDanService.findGiangVienByEmailAndCoSo(coSoIDSelected, userDetails.getEmail());
            session.setAttribute("giangVienHuongDang", giangVienHuongDan);
            return "redirect:/admin/hoc-ky";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            List<CoSo> listCoSo = coSoService.getListCoSo();
            model.addAttribute("listCoSo", listCoSo);
            return "authen/chon-co-so";
        }
    }
}
