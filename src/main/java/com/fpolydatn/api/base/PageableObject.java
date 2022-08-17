package com.fpolydatn.api.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author phongtt35
 */

@Getter
@AllArgsConstructor
public class PageableObject<T> {

    private List<T> data;
    private long totalPages;
    private int currentPage;
}
