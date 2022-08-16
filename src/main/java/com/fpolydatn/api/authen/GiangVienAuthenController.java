package com.fpolydatn.api.authen;

import com.fpolydatn.infrastructure.security.MyUserDetails;
import com.fpolydatn.infrastructure.security.UserDetailsService;
import com.fpolydatn.model.domain.CoSo;
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

    @GetMapping("/oauth2/authorization")
    public String chuyenTrang(HttpSession session, @RequestParam("coSoID") String coSoID) {
        session.setAttribute("coSoIDSelected", coSoID);
        return "redirect:/check-authen";
    }

    @GetMapping("/check-authen")
    public String checkAuthen(Model model, OAuth2AuthenticationToken token, HttpSession session) {
        MyUserDetails userDetails = userDetailsService.getUserDetails(token);
        String coSoIDSelected = (String) session.getAttribute("coSoIDSelected");
        try {
            giangVienHuongDanService.findGiangVienByEmailAndCoSo(coSoIDSelected, userDetails.getEmail());
            return "redirect:/admin/hoc-ky";
        } catch (Exception e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            List<CoSo> listCoSo = coSoService.getListCoSo();
//            model.addAttribute("listCoSo", listCoSo);
            return "redirect:/gv-chon-co-so";
//            return "authen/chon-co-so";
        }
    }
}
