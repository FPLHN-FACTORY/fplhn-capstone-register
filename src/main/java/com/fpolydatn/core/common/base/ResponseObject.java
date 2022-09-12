package com.fpolydatn.core.common.base;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nguyenvv4
 */
@Getter
@Setter
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
        }
    }
}
