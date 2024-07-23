package com.fpolydatn.infrastructure.security;

import com.fpolydatn.infrastructure.constant.ActorConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * @author HangNT
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private UserService userService;
    private AdminAuthProvider adminAuthProvider;

    public SecurityConfig(UserService userService, AdminAuthProvider adminAuthProvider) {
        this.userService = userService;
        this.adminAuthProvider = adminAuthProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and()
                .csrf().disable()
                .authorizeRequests(authReq -> authReq
                        .antMatchers("/authentication/*", "/").permitAll()
                        .antMatchers("/chu-nhiem/**").hasAuthority(ActorConstant.CHU_NHIEM)
                        .antMatchers("/dao-tao/**").hasAuthority(ActorConstant.DAO_TAO)
                        .antMatchers("/giang-vien/**").hasAuthority(ActorConstant.GIANG_VIEN)
                        .antMatchers("/sinh-vien/**").hasAuthority(ActorConstant.SINH_VIEN)
                        .antMatchers("/admin/**", "/admin").hasAuthority(ActorConstant.ADMIN)
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/")
                        .loginProcessingUrl("/admin-login")
                        .successHandler((req, response, auth) -> {
                            response.sendRedirect("/admin");
                        })
                        .permitAll()
                )
                .authenticationProvider(adminAuthProvider)
                .oauth2Login(oauth -> oauth
                        .successHandler((request, response, authentication) -> {
                            System.out.println(authentication);
                            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                            String role = oauthUser.getRole();
                            String coSoID = oauthUser.getCoSoId();
                            userService.processOAuthPostLogin(response, coSoID, oauthUser.getEmail(), oauthUser.getName(), role);
                        })
                        .failureUrl("/authentication")
                )
                .logout(out -> out
                        .logoutUrl("/doLogout")
                        .logoutSuccessUrl("/").permitAll()
                        .invalidateHttpSession(true)
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/authentication/403")
                );

        http.headers()
                .xssProtection(Customizer.withDefaults())
//                .contentSecurityPolicy(contentSecurityPolicyConfig ->
//                        contentSecurityPolicyConfig.policyDirectives("""
//                base-uri 'none'; default-src 'self' http://localhost:8080 https://dangky.poly.edu.vn;
//                child-src 'none'; connect-src 'self';
//                font-src 'self' https://fonts.googleapis.com https://maxcdn.bootstrapcdn.com https://cdnjs.cloudflare.com https://fonts.gstatic.com;
//                form-action 'self' http://localhost:8080/authentication https://dangky.poly.edu.vn; frame-ancestors 'none';
//                img-src 'self' data:; media-src 'self'; object-src 'none';
//                script-src 'self' http://localhost:8080 https://dangky.poly.edu.vn 'unsafe-inline' 'unsafe-eval';
//                style-src 'self' https://fonts.googleapis.com http://localhost:8080 https://dangky.poly.edu.vn https://cdnjs.cloudflare.com https://maxcdn.bootstrapcdn.com 'unsafe-inline'
//                                """.replace("\n", "")))
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