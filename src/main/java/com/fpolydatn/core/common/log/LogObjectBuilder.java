package com.fpolydatn.core.common.log;

import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public final class LogObjectBuilder {

    private final FpolyDatnSession session;
    private final HttpServletRequest request;

    @Autowired
    public LogObjectBuilder(FpolyDatnSession session
        , HttpServletRequest request
    ) {
        this.session = session;
        this.request = request;
    }

    public LogObject build(String content, String status) {
        LogObject logObject = buildDefault();
        logObject.setContent(content);
        logObject.setLogStatus(status);
        return logObject;
    }

    public LogObject buildDefault() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = simpleDateFormat.format(currentDate);

        LogObject logObject = new LogObject();

        logObject.setCreateDate(formattedDate);
        logObject.setTenDotDangKy(session.getDotDangKy());

        logObject.setCoSo(session.getCoSoId());
        logObject.setRole(session.getRole());
        logObject.setIp(request.getRemoteAddr());
        logObject.setMail(session.getEmail());

        logObject.setApi(request.getRequestURI());
        logObject.setHttpMethod(request.getMethod());
        logObject.setParam(StringUtil.toJson(request.getParameterMap()));

        return logObject;
    }

}
