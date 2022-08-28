package com.fpolydatn.util;

import java.sql.Timestamp;

/**
 * @author HangNT
 */
public class DateTimeUtil {

    public static Long convertDateToTimeStampSecond() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

}
