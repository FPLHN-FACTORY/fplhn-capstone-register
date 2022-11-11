package com.fpolydatn.infrastructure.security;

import com.fpolydatn.infrastructure.constant.SessionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author HangNT
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user =  super.loadUser(userRequest);

        String avatar = user.getAttribute("picture");
        session.setAttribute(SessionConstant.AVATAR, avatar);

        String role = (String) session.getAttribute(SessionConstant.ROLE);
        String coSoId = (String) session.getAttribute(SessionConstant.CO_SO_ID);

        CustomOAuth2User customUser = new CustomOAuth2User(user);
        customUser.setRole(role);
        customUser.setCoSoId(coSoId);

        return customUser;
    }

}
