package com.fpolydatn.infrastructure.logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggerObject {

    private String content;

    private String IP;

    private String mail;

    private String method;

    private String createDate;

    private String author;

    private String tenDotDangKy;

    private String status;
}
