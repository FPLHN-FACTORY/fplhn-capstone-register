package com.fpolydatn.infrastructure.security;

import com.fpolydatn.infrastructure.constant.ActorConstant;
import com.fpolydatn.infrastructure.constant.SessionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author HangNT
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerUserService customerUserService;


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customerUserService);
        return auth;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(customerUserService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/authentication/*", "/")
                .permitAll()
                .antMatchers("/chu-nhiem").hasAuthority(ActorConstant.CHU_NHIEM)
                .antMatchers("/dao-tao").hasAuthority(ActorConstant.DAO_TAO)
                .antMatchers("/giang-vien").hasAuthority(ActorConstant.GIANG_VIEN)
                .antMatchers("/sinh-vien").hasAuthority(ActorConstant.SINH_VIEN)
                .antMatchers("/admin").hasAuthority(ActorConstant.ADMIN)
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/authentication/login")
                .usernameParameter("email")
                .passwordParameter("pass")
//                .loginProcessingUrl("/chu-nhiem/dot-dang-ky/sinh-vien")
                .failureUrl("/authenticationication/login")
                .and()
                .logout().invalidateHttpSession(true).permitAll().and().csrf().disable().cors();
    }
}