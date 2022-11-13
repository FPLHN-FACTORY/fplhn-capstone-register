package com.fpolydatn.util;

import com.fpolydatn.infrastructure.constant.MailConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Async
    public void sendEmail(String[] toEmails, String subject, String titleEmail, String[] bodyEmail) {
        Map<String, Object> model = new HashMap<>();
        model.put("bodyEmail", bodyEmail);
        model.put("titleEmail", titleEmail);
        String htmlBody = loadMailBody(MailConstant.TEMPLATE_MAIL, model);
        sendSimpleMail(toEmails, htmlBody, subject);
    }

    private String loadMailBody(String templateName, Map<String, Object> model) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(model);
        return thymeleafTemplateEngine.process(templateName, thymeleafContext);
    }

    private void sendSimpleMail(String[] recipients, String msgBody, String subject) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(recipients);
            mimeMessageHelper.setText(msgBody, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("ERROR WHILE SENDING MAIL: {}", e.getMessage());
        }
    }
}
