package com.fpolydatn.infrastructure.mailler;

import com.fpolydatn.infrastructure.constant.MailConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.StandardCharsets;

@ComponentScan(basePackages = { "com.fpolydatn.util" })
public class MailTemplateConfig {
    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MailConstant.TEMPLATE_PREFIX);
        templateResolver.setSuffix(MailConstant.TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(MailConstant.TEMPLATE_MODE);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return templateResolver;
    }
}
