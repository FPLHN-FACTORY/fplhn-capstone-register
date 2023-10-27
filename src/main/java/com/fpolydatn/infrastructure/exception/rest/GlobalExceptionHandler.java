package com.fpolydatn.infrastructure.exception.rest;

import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.SessionConstant;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.util.EmailSender;
import com.fpolydatn.util.WriterFileCSV;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@RestControllerAdvice
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

    @Autowired
    private WriterFileCSV writerFileCSV;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex) throws IOException {
        if (ex instanceof RestApiException) {
            ApiError apiError = new ApiError(ex.getMessage());
            HttpServletRequest requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
            String requestBodyContentCopy = new String(((ContentCachingRequestWrapper) requestWrapper).getContentAsByteArray());
            ghiLogCSVBug(ex, writerFileCSV.getPathFileBadRequest(), requestBodyContentCopy);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof ConstraintViolationException) {
            HttpServletRequest requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
            String requestBodyContentCopy = new String(((ContentCachingRequestWrapper) requestWrapper).getContentAsByteArray());
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
            List<ErrorModel> errors = violations.stream()
                    .map(violation ->
                            new ErrorModel(getPropertyName(violation.getPropertyPath()), violation.getMessage()))
                    .collect(Collectors.toList());
            ghiLogCSVBug(ex, writerFileCSV.getPathFileBadRequest(), requestBodyContentCopy);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else {
            String requestBodyContent = "";
            BufferedReader reader = httpServletRequest.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            requestBodyContent = requestBody.toString();
            ghiLogCSVBug(ex, writerFileCSV.getPathFile500(), requestBodyContent);
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

    private void ghiLogCSVBug(Exception ex, String pathFile, String requestBodyContent) throws IOException {
        Gson gson = new Gson();
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setStatus(ConfigurationsConstant.FALSE);
        loggerObject.setPathFile(pathFile);
        loggerObject.setContent(ex.toString());
        loggerObject.setMail(session.getAttribute(SessionConstant.EMAIL) == null ? "" : session.getAttribute(SessionConstant.EMAIL).toString());
        loggerObject.setIP(httpServletRequest.getRemoteAddr());
        loggerObject.setRequest(requestBodyContent);
        loggerObject.setMethod(httpServletRequest.getMethod());
        loggerObject.setApi(httpServletRequest.getRequestURI());
        loggerObject.setParam(gson.toJson(httpServletRequest.getParameterMap()));
        service.sendLogMessage(loggerObject);
        System.out.println(" Đã ghi log");
    }

    private String getPropertyName(Path path) {
        String pathStr = path.toString();
        String[] comps = pathStr.split("\\.");
        if (comps.length > 0) {
            return comps[comps.length - 1];
        } else {
            return pathStr;
        }
    }
}
