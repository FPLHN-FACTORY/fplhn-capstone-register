//package com.fpolydatn.infrastructure.exception.rest;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
///**
// * @author nguyenvv4
// */
//@RestControllerAdvice
//public class RestApiExceptionHandler {
//
//    @ExceptionHandler(RestApiException.class)
//    public ResponseEntity<?> handlerException(RestApiException restApiException) {
//        ApiError apiError = new ApiError(restApiException.getMessage());
//        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//    }
//
//}
