package com.fpolydatn.infrastructure.configuration;

import com.fpolydatn.infrastructure.constant.MailConstant;
import org.springframework.context.annotation.*;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.StandardCharsets;

@Configuration
public class MailTemplateConfig {
    @Bean
    public ITemplateResolver thymeleafClassLoaderTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MailConstant.TEMPLATE_PREFIX);
        templateResolver.setSuffix(MailConstant.TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(MailConstant.TEMPLATE_MODE);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }
}
