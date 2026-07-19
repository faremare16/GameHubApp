package com.faruk.backend;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.HashMap;

@ControllerAdvice //this tells that this class sees all exceptions in the aplication
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error", ex.getMessage());

        if(ex.getMessage().contains("invalid") || ex.getMessage().contains("credentials"))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
}
