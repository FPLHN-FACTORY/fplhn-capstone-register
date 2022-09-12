package com.fpolydatn.util;

/**
 * @author phongtt35
 */
public class PaginationUtil {

    private PaginationUtil() {}

    public static long calculateTotalPages(Long totalRecord, Integer pageSize) {
        return (totalRecord % pageSize == 0 ? (totalRecord / pageSize) : (totalRecord / pageSize + 1));
    }
}
