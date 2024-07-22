package com.fpolydatn.infrastructure.security;

import com.fpolydatn.infrastructure.constant.ActorConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Component
public class AdminAuthProvider implements AuthenticationProvider {

    @Value("${fcr.admin.username}")
    private String adminUsername;

    @Value("${fcr.admin.password}")
    private String adminPassword;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            UserDetails adminUser = User.withUsername(adminUsername)
                    .password(adminPassword)
                    .authorities(ActorConstant.ADMIN)
                    .build();
            return new UsernamePasswordAuthenticationToken(adminUser, password, adminUser.getAuthorities());
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
