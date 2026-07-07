package com.example.demo.exception;

public class TrainerNotFoundException extends RuntimeException {
    public TrainerNotFoundException(String errorMsg){
        super(errorMsg);
    }
}
