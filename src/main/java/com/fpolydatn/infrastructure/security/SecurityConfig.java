package com.fpolydatn.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import javax.servlet.http.HttpSession;

/**
 * @author HangNT
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/index", "/gv-chon-co-so", "/cb-chon-co-so", "/sv-chon-co-so", "/check-authen", "/login-co-so", "/test", "/login-co-so-check")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .successHandler((request, response, authentication) -> {
                    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                    String coSoID = (String) session.getAttribute("coSoIDSelected");
                    userService.processOAuthPostLogin(response,coSoID, oauthUser.getEmail());
                    response.sendRedirect("/admin/hoc-ky");
                })
                .failureHandler((request, response, exception) -> {

                });

    }

}
