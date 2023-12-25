package com.fpolydatn.core.common.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class LogInterceptor implements HandlerInterceptor {

    private final IDatnLogger logger;

    @Autowired
    public LogInterceptor(IDatnLogger logger) {
        this.logger = logger;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        if (ex != null) {
            logger.log("ACCESS", "OK");
        } else {
            logger.log("", "NOT OK");
        }
    }
}
