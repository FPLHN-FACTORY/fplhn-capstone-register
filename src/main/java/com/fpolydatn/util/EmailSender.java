package com.fpolydatn.util;

import com.fpolydatn.infrastructure.constant.MailConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public void sendSimpleMail(String[] recipients, String msgBody, String subject) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(recipients);
            mimeMessageHelper.setText(msgBody, true);
            mimeMessageHelper.setSubject(subject);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("ERROR WHILE SENDING MAIL: {}", e.getMessage());
        }
    }

    public void sendRegisterPasswordMail(String toEmail, String name, String password) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("password", password);
        String htmlBody = loadMailBody(MailConstant.TEMPLATE_REGISTER_PASSWORD, model);

        sendSimpleMail(new String[]{toEmail}, htmlBody, String.format(MailConstant.SUBJECT_REGISTER_PASSWORD, name));
    }

    private String loadMailBody(String templateName, Map<String, Object> model) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(model);
        return thymeleafTemplateEngine.process(templateName, thymeleafContext);
    }
}
