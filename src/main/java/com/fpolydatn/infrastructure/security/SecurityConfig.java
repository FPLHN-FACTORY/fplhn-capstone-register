package com.fpolydatn.infrastructure.security;

import com.fpolydatn.infrastructure.constant.ActorConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import java.util.Objects;

/**
 * @author HangNT
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository()))
                .authorizeRequests()
                .antMatchers("/authentication/*", "/", "/admin/bit-dev").permitAll()
                .antMatchers("/chu-nhiem/**").hasAuthority(ActorConstant.CHU_NHIEM)
                .antMatchers("/dao-tao/**").hasAuthority(ActorConstant.DAO_TAO)
                .antMatchers("/giang-vien/**").hasAuthority(ActorConstant.GIANG_VIEN)
                .antMatchers("/sinh-vien/**").hasAuthority(ActorConstant.SINH_VIEN)
                .antMatchers("/admin/bit-dev/**").hasAuthority(ActorConstant.ADMIN)
                .and()
                .oauth2Login()
                .successHandler((request, response, authentication) -> {
                    CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                    String role = oauthUser.getRole();
                    String coSoID = oauthUser.getCoSoId();
                    if (Objects.isNull(role) || Objects.isNull(coSoID)) {
                        if (!ActorConstant.ADMIN.equals(role)) {
                            response.sendRedirect("/");
                        }
                    }
                    userService.processOAuthPostLogin(response, coSoID, oauthUser.getEmail(), oauthUser.getName(), role);
                })
                .failureUrl("/authentication")
                .and()
                .logout()
                .logoutUrl("/doLogout")
                .logoutSuccessUrl("/").permitAll()
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/authentication/403");

        http.headers()
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"))
                //Content Security Policy (CSP) Header Not Set
//                .addHeaderWriter(
//                        new StaticHeadersWriter(
//                                "Content-Security-Policy",
//                                "default-src 'self'; " +
//                                "style-src 'self' https://cdnjs.cloudflare.com https://maxcdn.bootstrapcdn.com https://fonts.googleapis.com https://harvesthq.github.io/chosen/ http://code.jquery.com/ui/1.13.2/themes/base/ 'unsafe-inline'; " +
//                                "font-src 'self' https://cdnjs.cloudflare.com; " +
//                                "img-src 'self' https://ap.poly.edu.vn https://lh3.googleusercontent.com https://gv.poly.edu.vn; " + // Added https://gv.poly.edu.vn
//                                "script-src 'self' https://code.jquery.com https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js https://gv.poly.edu.vn 'unsafe-inline'" // Added https://gv.poly.edu.vn
//                        )
//                )
                .frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**", "/css/**");
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

}