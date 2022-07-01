package com.fpolydatn.api.response;

import lombok.Data;

/**
 * @author nguyenvv4
 */
@Data
public class ResponseObject {

    private boolean isSuccess = false;
    private Object data;
    private String message;

    public <T> ResponseObject(T obj) {
        processReponseObject(obj);
    }

    public void processReponseObject(Object obj) {
        if (obj != null) {
            this.isSuccess = true;
            this.data = obj;
//            this.errorCode = ErrorCode.SUCCESS.toString();
        }
    }
}
