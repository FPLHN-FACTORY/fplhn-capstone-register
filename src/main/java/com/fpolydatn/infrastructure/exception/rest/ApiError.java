package com.fpolydatn.infrastructure.exception.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nguyenvv4
 */
@AllArgsConstructor
@Getter
@Setter
public class ApiError {

    private String message;

}
