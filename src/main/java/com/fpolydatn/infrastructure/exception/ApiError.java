package com.fpolydatn.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author nguyenvv4
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {

    private String message;

}
