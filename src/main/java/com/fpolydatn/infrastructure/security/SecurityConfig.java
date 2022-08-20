package com.fpolydatn.infrastructure.security;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
//import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author HangNT
 */
//@Configuration
//@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private HttpSession session;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.cors().and().csrf().disable().authorizeRequests()
//                .antMatchers("/authentication/*", "/")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2Login()
//                .successHandler((request, response, authentication) -> {
//                    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
//                    String role = (String) session.getAttribute("role");
//                    String coSoID = (String) session.getAttribute("coSoIDSelected");
//                    if (Objects.isNull(role) || Objects.isNull(coSoID)) {
//                        response.sendRedirect("/");
//                    } else {
//                        userService.processOAuthPostLogin(response, coSoID, oauthUser.getEmail(), role);
//                    }
//                })
//                .failureUrl("/error")
//                .and().logout().logoutSuccessUrl("/").permitAll();
//
//        return http.build();
//    }

}
