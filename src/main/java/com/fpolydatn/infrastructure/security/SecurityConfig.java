package com.fpolydatn.infrastructure.security;

import com.fpolydatn.infrastructure.constant.ActorConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;

/**
 * @author HangNT
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/authentication/*", "/").permitAll()
                .antMatchers("/chu-nhiem/**").hasAuthority(ActorConstant.CHU_NHIEM)
                .antMatchers("/dao-tao/**").hasAuthority(ActorConstant.DAO_TAO)
                .antMatchers("/giang-vien/**").hasAuthority(ActorConstant.GIANG_VIEN)
                .antMatchers("/sinh-vien/**").hasAuthority(ActorConstant.SINH_VIEN)
                .antMatchers("/admin/**").hasAuthority(ActorConstant.ADMIN)
                .and()
                    .oauth2Login()
                    .successHandler((request, response, authentication) -> {
                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                        String role = oauthUser.getRole();
                        String coSoID = oauthUser.getCoSoId();
                        if (Objects.isNull(role) || Objects.isNull(coSoID)) {
                            response.sendRedirect("/");
                        } else {
                            userService.processOAuthPostLogin(response, coSoID, oauthUser.getEmail(), role);
                        }
                    })
                    .failureUrl("/authentication")
                .and()
                    .logout()
                    .logoutUrl("/doLogout")
                    .logoutSuccessUrl("/").permitAll()
                    .invalidateHttpSession(true)
                .and()
                    .exceptionHandling().accessDeniedPage("/authentication/403");

        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**", "/css/**");
    }

}