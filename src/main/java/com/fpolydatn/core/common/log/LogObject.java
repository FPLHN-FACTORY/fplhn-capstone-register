package com.fpolydatn.core.common.log;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class LogObject implements Serializable {

    // Time
    private String createDate;
    private String tenDotDangKy;

    // Identity
    private String coSo;
    private String role;
    private String ip;
    private String mail;

    // Result
    private String content;
    private String logStatus;

    // Input
    private String api;
    private String httpMethod;
    private String param;
//    private String bodyRequest;
}
