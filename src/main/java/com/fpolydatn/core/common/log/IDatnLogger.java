package com.fpolydatn.core.common.log;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "singleton")
public interface IDatnLogger {

    void log(LogObject data);

    void log(String content, String status);

}
