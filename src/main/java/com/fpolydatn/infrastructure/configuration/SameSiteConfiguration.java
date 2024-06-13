package com.fpolydatn.infrastructure.configuration;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Configuration(proxyBeanMethods = false)
public class SameSiteConfiguration {

    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        // Cookie without SameSite Attribute - Low
        // Resolved: change lax to strict
        return CookieSameSiteSupplier.ofStrict();
    }
}
