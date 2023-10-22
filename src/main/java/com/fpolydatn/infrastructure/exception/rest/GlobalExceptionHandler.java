package com.fpolydatn.infrastructure.exception.rest;

import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.SessionConstant;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.util.EmailSender;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author thangncph26123
 */
@ControllerAdvice
public final class GlobalExceptionHandler {

    @Autowired
    private LogService service;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private EmailSender emailSender;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex) throws IOException {
        if (ex instanceof RestApiException) {
            ApiError apiError = new ApiError(ex.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        } else {
            Gson gson = new Gson();
            BufferedReader reader = httpServletRequest.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            String requestBodyContent = requestBody.toString();
            LoggerObject loggerObject = new LoggerObject();
            loggerObject.setStatus(ConfigurationsConstant.FALSE);
            loggerObject.setContent(ex.toString());
            loggerObject.setMail(session.getAttribute(SessionConstant.EMAIL) == null ? "" : session.getAttribute(SessionConstant.EMAIL).toString());
            loggerObject.setIP(httpServletRequest.getRemoteAddr());
            loggerObject.setRequest(requestBodyContent);
            loggerObject.setMethod(httpServletRequest.getMethod());
            loggerObject.setApi(httpServletRequest.getRequestURI());
            loggerObject.setParam(gson.toJson(httpServletRequest.getParameterMap()));
            service.sendLogMessage(loggerObject);
            System.out.println(ex + " có bug có bug.");
            String[] emailArray = new String[1];
            emailArray[0] = mailUsername;
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    emailSender.sendEmail(emailArray, "[FCR] Thông báo có lỗi hệ thống",
                            "[FCR] Thông báo có lỗi hệ thống",
                            new String[]{ex.toString()});
                }
            };
            Thread myThread = new Thread(myRunnable);
            myThread.start();
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
