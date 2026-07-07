package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<?> handleTrainer(Exception e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUser(Exception e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validateField(MethodArgumentNotValidException ex){
        
        Map<String,String> error = new HashMap<>();

        ex.
        getBindingResult().
        getFieldErrors().
        forEach(err -> error.put(err.getField(),err.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    
}
