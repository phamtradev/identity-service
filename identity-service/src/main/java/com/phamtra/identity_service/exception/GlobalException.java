package com.phamtra.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<String> handlIdException(IdInvalidException idInvalidException) {
        return ResponseEntity.badRequest().body(idInvalidException.getMessage());
    }
}
