package com.fpolydatn.api.authen;

import com.fpolydatn.infrastructure.security.UserDetailsService;
import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.service.CoSoService;
import com.fpolydatn.service.GiangVienHuongDanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HangNT
 */
@Controller
@RequiredArgsConstructor
public class CanBoAuthenController {
    @Autowired
    private CoSoService coSoService;

    @Autowired
    private GiangVienHuongDanService giangVienHuongDanService;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    private static String authorizationRequestBaseUri = "oauth2/authorization";
    private Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    private final UserDetailsService userDetailsService;

    @GetMapping("/cb-chon-co-so")
    public String getCoSo(Model model) {
        List<CoSo> listCoSo = coSoService.getListCoSo();
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        model.addAttribute("listCoSo", listCoSo);
        return "authen/chon-co-so";
    }
}
