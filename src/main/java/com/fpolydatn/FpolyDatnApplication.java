package com.fpolydatn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author nguyenvv4
 */

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class FpolyDatnApplication {

    public static void main(String[] args) {
        SpringApplication.run(FpolyDatnApplication.class, args);
    }

}
