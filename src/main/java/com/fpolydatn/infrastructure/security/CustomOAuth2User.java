package com.fpolydatn.infrastructure.security;

import com.fpolydatn.infrastructure.constant.ActorConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author HangNT
 */
public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;

    private String role;
    private String coSoId;

    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        switch (role) {
            case ActorConstant.CHU_NHIEM -> authorities.add(new SimpleGrantedAuthority(ActorConstant.CHU_NHIEM));
            case ActorConstant.DAO_TAO -> authorities.add(new SimpleGrantedAuthority(ActorConstant.DAO_TAO));
            case ActorConstant.GIANG_VIEN -> authorities.add(new SimpleGrantedAuthority(ActorConstant.GIANG_VIEN));
            case ActorConstant.SINH_VIEN -> authorities.add(new SimpleGrantedAuthority(ActorConstant.SINH_VIEN));
            //case ActorConstant.ADMIN -> authorities.add(new SimpleGrantedAuthority(ActorConstant.ADMIN));
        }
        return authorities;
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.getAttribute("email");
    }

    public String getAvatar() {
        return oauth2User.getAttribute("picture");
    }

    public void setCoSoId(String coSoId) {
        this.coSoId = coSoId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public String getCoSoId() {
        return this.coSoId;
    }
}
